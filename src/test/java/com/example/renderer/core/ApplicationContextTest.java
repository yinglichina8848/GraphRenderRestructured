package com.example.renderer.core;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.bridge.RendererFactory;
import com.example.renderer.config.GlobalConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

class ApplicationContextTest {
    
    @AfterEach
    void tearDown() {
        ApplicationContext.refresh();
    }

    @Test
    void testGetBean_withDefaultRenderer() {
        // 模拟依赖
        GlobalConfig mockConfig = mock(GlobalConfig.class);
        when(mockConfig.getRenderMode()).thenReturn("SWING");
        
        Renderer mockRenderer = mock(Renderer.class);
        
        // 重写静态块中的注册
        ApplicationContext.register(Renderer.class, () -> {
            GlobalConfig config = GlobalConfig.getInstance();
            return RendererFactory.create(config.getRenderMode());
        });
        
        // 测试
        Renderer renderer = ApplicationContext.getBean(Renderer.class);
        assertNotNull(renderer);
    }

    @Test
    void testRegisterAndGetBean() {
        // 设置测试用例
        class TestBean {}
        TestBean testInstance = new TestBean();
        
        // 注册自定义组件
        ApplicationContext.register(TestBean.class, () -> testInstance);
        
        // 获取并验证
        TestBean bean = ApplicationContext.getBean(TestBean.class);
        assertSame(testInstance, bean);
    }

    @Test
    void testGetBean_missingRegistration() {
        class UnregisteredBean {}
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> ApplicationContext.getBean(UnregisteredBean.class)
        );
        assertEquals("No supplier registered for class com.example.renderer.core.ApplicationContextTest$1UnregisteredBean",
                     exception.getMessage());
    }

    @Test
    void testRefreshClearsCachedBeans() {
        // 注册并获取一次
        class CachedBean {}
        CachedBean instance1 = new CachedBean();
        ApplicationContext.register(CachedBean.class, () -> instance1);
        CachedBean bean1 = ApplicationContext.getBean(CachedBean.class);
        
        // 刷新后获取新实例
        ApplicationContext.refresh();
        CachedBean instance2 = new CachedBean();
        ApplicationContext.register(CachedBean.class, () -> instance2);
        CachedBean bean2 = ApplicationContext.getBean(CachedBean.class);
        
        assertNotSame(bean1, bean2);
    }
    
    @Test
    void testConcurrentAccess() throws InterruptedException {
        class ThreadSafeBean {}
        final int[] instanceCount = {0};
        
        // 注册一个线程安全的组件
        ApplicationContext.register(ThreadSafeBean.class, () -> {
            instanceCount[0]++;
            return new ThreadSafeBean();
        });
        
        // 创建多个线程并发访问
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                ApplicationContext.getBean(ThreadSafeBean.class);
            });
        }
        
        // 启动所有线程
        for (Thread t : threads) {
            t.start();
        }
        
        // 等待完成
        for (Thread t : threads) {
            t.join(1000);
        }
        
        // 验证只创建了一个实例
        assertEquals(1, instanceCount[0]);
    }

    /**
     * 测试双重检查锁的正确执行路径
     */
    @Test
    void testDoubleCheckedLocking() throws InterruptedException {
        class TestDoubleCheckBean {}
        
        // 使用 CountDownLatch 控制线程执行顺序
        CountDownLatch enterWriteLockLatch = new CountDownLatch(1);
        AtomicBoolean firstThreadEnteredWriteLock = new AtomicBoolean(false);
        
        ApplicationContext.register(TestDoubleCheckBean.class, () -> {
            // 当第一个线程进入写锁后，允许其他线程继续
            if (firstThreadEnteredWriteLock.compareAndSet(false, true)) {
                enterWriteLockLatch.countDown();
            }
            return new TestDoubleCheckBean();
        });
        
        Thread thread1 = new Thread(() -> {
            ApplicationContext.getBean(TestDoubleCheckBean.class);
        });
        
        Thread thread2 = new Thread(() -> {
            try {
                // 等待第一个线程进入写锁
                enterWriteLockLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            ApplicationContext.getBean(TestDoubleCheckBean.class);
        });
        
        thread1.start();
        thread2.start();
        
        thread1.join(2000);
        thread2.join(2000);
        
        // 验证没有异常发生
        assertTrue(firstThreadEnteredWriteLock.get());
    }

    /**
     * 测试静态初始化块中的默认 Renderer 注册
     */
    @Test
    void testStaticInitializerRegistration() {
        // 获取 Renderer 的 bean 实例应该成功
        Renderer renderer = ApplicationContext.getBean(Renderer.class);
        assertNotNull(renderer);
    }
    
    /**
     * 测试 Supplier 返回 null 的情况
     */
    @Test
    void testSupplierReturnsNull() {
        class TestBean {}
        
        ApplicationContext.register(TestBean.class, () -> null);
        
        // 第一次调用时应返回 null
        TestBean bean1 = ApplicationContext.getBean(TestBean.class);
        assertNull(bean1);
        
        // 后续调用应始终返回 null（缓存机制）
        TestBean bean2 = ApplicationContext.getBean(TestBean.class);
        assertNull(bean2);
    }
    
    /**
     * 测试异常链的完整处理
     */
    @Test
    void testNestedExceptionHandling() {
        class ErrorBean {}
        
        // 设置供应商在调用时抛出多个嵌套异常
        ApplicationContext.register(ErrorBean.class, () -> {
            throw new IllegalStateException("Outer exception", 
                new RuntimeException("Inner exception"));
        });
        
        Exception exception = assertThrows(
            IllegalStateException.class,
            () -> ApplicationContext.getBean(ErrorBean.class)
        );
        
        assertTrue(exception.getMessage().contains("Outer exception"));
        assertNotNull(exception.getCause());
        assertEquals("Inner exception", exception.getCause().getMessage());
    }
}

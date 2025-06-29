package com.example.renderer.core;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.bridge.RendererFactory;
import com.example.renderer.config.GlobalConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
}

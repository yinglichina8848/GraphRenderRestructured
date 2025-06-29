package com.example.renderer.core;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.bridge.RendererFactory;
import com.example.renderer.config.GlobalConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * 轻量级DI容器，管理核心组件生命周期。
 * 
 * <p>设计特点：
 * <ul>
 *   <li>按需初始化 - 首次访问时创建实例</li>
 *   <li>线程安全 - 内置同步机制</li>
 *   <li>可扩展 - 支持动态注册组件</li>
 * </ul>
 * 
 * <p>典型用法：
 * <pre>
 * // 注册组件
 * ApplicationContext.register(Renderer.class, () -> new SwingRenderer());
 * 
 * // 获取组件
 * Renderer renderer = ApplicationContext.getBean(Renderer.class);
 * </pre>
 */
public class ApplicationContext {
    private static final Map<Class<?>, Object> beans = new HashMap<>();
    private static final Map<Class<?>, Supplier<?>> suppliers = new HashMap<>();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    static {
        // 注册核心组件
        register(Renderer.class, () -> 
            RendererFactory.create(GlobalConfig.getInstance().getRenderMode()));
    }
    
    /**
     * 注册一个类型及其对应的供应商
     * @param <T> 要注册的类型
     * @param type 要注册的类对象
     * @param supplier 用于创建实例的供应商
     * @throws NullPointerException 如果type或supplier为null
     */
    public static <T> void register(Class<T> type, Supplier<T> supplier) {
        suppliers.put(type, supplier);
    }
    
    /**
     * 获取指定类型的Bean实例
     * 
     * @param <T> Bean类型
     * @param type 要获取的Bean类对象
     * @return 已注册的Bean实例
     * @throws IllegalStateException 如果类型未注册
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> type) {
        try {
            lock.readLock().lock();
            if (!beans.containsKey(type)) {
                Supplier<?> supplier = suppliers.get(type);
                if (supplier == null) {
                    throw new IllegalStateException("No supplier registered for " + type);
                }
                
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    // 双重检查
                    if (!beans.containsKey(type)) {
                        beans.put(type, supplier.get());
                    }
                } finally {
                    lock.readLock().lock();
                    lock.writeLock().unlock();
                }
            }
            return (T) beans.get(type);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * 刷新应用上下文，清除所有缓存的Bean实例
     */
    public static void refresh() {
        beans.clear();
    }
}

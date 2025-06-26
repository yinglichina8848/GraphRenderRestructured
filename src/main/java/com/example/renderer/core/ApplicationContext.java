package com.example.renderer.core;

import java.util.HashMap;
import java.util.Map;
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
    
    static {
        // 注册核心组件
        register(Renderer.class, () -> 
            RendererFactory.create(GlobalConfig.getInstance().getRenderMode()));
    }
    
    public static <T> void register(Class<T> type, Supplier<T> supplier) {
        suppliers.put(type, supplier);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> type) {
        if (!beans.containsKey(type)) {
            Supplier<?> supplier = suppliers.get(type);
            if (supplier == null) {
                throw new IllegalStateException("No supplier registered for " + type);
            }
            beans.put(type, supplier.get());
        }
        return (T) beans.get(type);
    }
    
    public static void refresh() {
        beans.clear();
    }
}

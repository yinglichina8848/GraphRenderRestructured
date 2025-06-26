package com.example.renderer.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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

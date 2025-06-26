package com.example.renderer.bridge;

import com.example.renderer.adapter.LegacyRendererAdapter;
import com.example.renderer.adapter.LegacyRendererImpl;
import com.example.renderer.exception.RendererCreationException;
import com.example.renderer.legacy.LegacyRenderer;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

/**
 * 渲染器工厂，支持动态扩展渲染器实现。
 * 
 * <p>扩展机制：
 * <pre>
 * // 注册自定义渲染器
 * RendererFactory.register("custom", CustomRenderer::new);
 * 
 * // 创建实例
 * Renderer renderer = RendererFactory.create("custom");
 * </pre>
 * 
 * <p>内置渲染器：
 * <ul>
 *   <li>swing - SwingRenderer</li>
 *   <li>svg - SVGRenderer</li>
 *   <li>test - TestRenderer</li>
 *   <li>legacy - LegacyRendererAdapter</li>
 * </ul>
 */
public class RendererFactory {
    private static final ConcurrentMap<String, Supplier<Renderer>> renderers = new ConcurrentHashMap<>();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    static {
        // 内置渲染器注册
        registerInternal("swing", SwingRenderer::new);
        registerInternal("svg", SVGRenderer::new);
        registerInternal("test", TestRenderer::new);
        registerInternal("legacy", () -> new LegacyRendererAdapter(new com.example.renderer.adapter.LegacyRendererImpl()));
    }
    
    private static void registerInternal(String mode, Supplier<Renderer> supplier) {
        renderers.put(mode, supplier);
    }
    
    /**
     * 注册一个新的渲染器实现
     * 
     * @param mode 渲染模式名称
     * @param supplier 渲染器供应商
     */
    public static void register(String mode, Supplier<Renderer> supplier) {
        renderers.put(mode, supplier);
    }
    
    /**
     * 创建指定模式的渲染器实例
     * 
     * @param mode 渲染模式名称
     * @return 渲染器实例
     * @throws RendererCreationException 如果创建失败
     */
    public static Renderer create(String mode) throws RendererCreationException {
        try {
            Supplier<Renderer> supplier = renderers.get(mode);
            if (supplier == null) {
                throw new IllegalArgumentException("Unsupported render mode: " + mode);
            }
            Renderer renderer = supplier.get();
            if (renderer == null) {
                throw new RendererCreationException("Renderer creation failed for mode: " + mode);
            }
            return renderer;
        } catch (Exception e) {
            throw new RendererCreationException("Failed to create renderer", e);
        }
    }
    
    /**
     * 获取所有支持的渲染模式
     * 
     * @return 支持的渲染模式集合
     */
    public static Set<String> getSupportedModes() {
        return Collections.unmodifiableSet(renderers.keySet());
    }
}

package com.example.renderer.bridge;

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
    private static final Map<String, Supplier<Renderer>> renderers = new HashMap<>();
    
    static {
        register("swing", SwingRenderer::new);
        register("svg", SVGRenderer::new);
        register("test", TestRenderer::new);
        register("legacy", () -> new LegacyRendererAdapter(new LegacyRenderer()));
    }
    
    public static void register(String mode, Supplier<Renderer> supplier) {
        renderers.put(mode, supplier);
    }
    
    public static Renderer create(String mode) {
        Supplier<Renderer> supplier = renderers.get(mode);
        if (supplier == null) {
            throw new IllegalArgumentException("Unsupported render mode: " + mode);
        }
        return supplier.get();
    }
    
    public static Set<String> getSupportedModes() {
        return Collections.unmodifiableSet(renderers.keySet());
    }
}

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
    private static final ConcurrentMap<String, Supplier<Renderer>> renderers = new ConcurrentHashMap<>();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    static {
        // 内置渲染器注册
        registerInternal("swing", SwingRenderer::new);
        registerInternal("svg", SVGRenderer::new);
        registerInternal("test", TestRenderer::new);
        registerInternal("legacy", () -> new LegacyRendererAdapter(new LegacyRenderer()));
    }
    
    private static void registerInternal(String mode, Supplier<Renderer> supplier) {
        renderers.put(mode, supplier);
    }
    
    public static void register(String mode, Supplier<Renderer> supplier) {
        renderers.put(mode, supplier);
    }
    
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
    
    public static Set<String> getSupportedModes() {
        return Collections.unmodifiableSet(renderers.keySet());
    }
}

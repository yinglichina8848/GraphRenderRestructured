package com.example.renderer.bridge;

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

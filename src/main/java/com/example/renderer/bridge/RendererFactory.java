package com.example.renderer.bridge;

public class RendererFactory {
    public static Renderer create(String mode) {
        switch(mode) {
            case "swing": return new SwingRenderer();
            case "svg": return new SVGRenderer();
            case "test": return new TestRenderer();
            case "legacy": return new LegacyRendererAdapter(new LegacyRenderer());
            default: throw new IllegalArgumentException("Unsupported render mode: " + mode);
        }
    }
}

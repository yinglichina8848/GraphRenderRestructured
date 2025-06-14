/**
 * LegacyRendererAdapter
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.adapter;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.legacy.LegacyRenderer;

/**
 * LegacyRendererAdapter 将旧版 LegacyRenderer 适配到新的 Renderer 接口，
 * 使得旧代码可以在新系统中复用。
 */
public class LegacyRendererAdapter implements Renderer {

    private final LegacyRenderer legacyRenderer;

    public LegacyRendererAdapter(LegacyRenderer legacyRenderer) {
        this.legacyRenderer = legacyRenderer;
    }

    @Override
    public void drawCircle(int x, int y, int radius) {
        legacyRenderer.drawLegacyCircle(x, y, radius);
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        legacyRenderer.drawLegacyRectangle(x, y, width, height);
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        legacyRenderer.drawLegacyTriangle(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void drawEllipse(int x, int y, int rx, int ry) {
        legacyRenderer.drawLegacyEllipse(x, y, rx, ry);
    }
}


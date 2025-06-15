/**
 * 适配器类，将LegacyRenderer接口适配到新的Renderer接口。
 * 实现了适配器模式，使旧版渲染器可以在新系统中使用。
 * 
 * <p>主要功能：
 * <ul>
 *   <li>将drawCircle()适配到drawLegacyCircle()</li>
 *   <li>将drawRectangle()适配到drawLegacyRectangle()</li>
 *   <li>将drawTriangle()适配到drawLegacyTriangle()</li>
 *   <li>将drawEllipse()适配到drawLegacyEllipse()</li>
 * </ul>
 * 
 * @see Renderer
 * @see LegacyRenderer
 * @author liying
 * @since 1.0
 */
package com.example.renderer.adapter;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.legacy.LegacyRenderer;

/**
 * LegacyRendererAdapter实现了适配器模式，将旧版LegacyRenderer接口
 * 适配到新的Renderer接口，使得旧代码可以在新系统中复用。
 * 
 * <p>适配器模式在这里用于解决新旧接口不兼容的问题。</p>
 * 
 * @see Renderer
 * @see LegacyRenderer
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


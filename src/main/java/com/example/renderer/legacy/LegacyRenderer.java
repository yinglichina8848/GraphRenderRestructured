package com.example.renderer.legacy;

/**
 * 旧版渲染器接口，定义了旧版系统的绘图方法。
 * 新系统通过LegacyRendererAdapter适配此接口。
 */
public interface LegacyRenderer {
    void drawLegacyCircle(int x, int y, int radius);
    void drawLegacyRectangle(int x, int y, int width, int height);
    void drawLegacyTriangle(int x1, int y1, int x2, int y2, int x3, int y3);
    void drawLegacyEllipse(int x, int y, int rx, int ry);
}

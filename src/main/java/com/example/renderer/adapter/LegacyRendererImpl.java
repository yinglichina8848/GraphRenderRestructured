package com.example.renderer.adapter;

import com.example.renderer.legacy.LegacyRenderer;

/**
 * LegacyRenderer接口的默认实现类
 */
public class LegacyRendererImpl implements LegacyRenderer {
    @Override
    public void drawLegacyCircle(int x, int y, int radius) {
        System.out.printf("LegacyRenderer: 绘制圆形，中心(%d,%d)，半径 %d\n", x, y, radius);
    }

    @Override 
    public void drawLegacyRectangle(int x, int y, int width, int height) {
        System.out.printf("LegacyRenderer: 绘制矩形，左上角(%d,%d)，宽 %d，高 %d\n", x, y, width, height);
    }

    @Override
    public void drawLegacyTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("LegacyRenderer: 绘制三角形，顶点(%d,%d),(%d,%d),(%d,%d)\n",
            x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void drawLegacyEllipse(int x, int y, int rx, int ry) {
        System.out.printf("LegacyRenderer: 绘制椭圆，中心(%d,%d)，X半轴 %d，Y半轴 %d\n", x, y, rx, ry);
    }
}

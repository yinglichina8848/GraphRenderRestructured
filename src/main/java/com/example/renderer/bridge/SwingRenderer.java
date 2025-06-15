package com.example.renderer.bridge;

/**
 * SwingRenderer实现了使用Java Swing进行图形渲染。
 * 作为Renderer接口的具体实现，它使用Graphics2D进行实际绘制。
 * 
 * <p>该类主要特点：
 * <ul>
 *   <li>使用蓝色(BLUE)作为默认绘制颜色</li>
 *   <li>使用2像素宽的线条</li>
 *   <li>需要先调用setGraphics()设置绘图上下文</li>
 * </ul>
 * 
 * @see Renderer
 * @see Graphics2D
 * @author liying
 * @since 2025-06-14
 */

import java.awt.Graphics2D;
import java.awt.Polygon;

public class SwingRenderer implements Renderer {
    private Graphics2D g;

    public void setGraphics(Graphics2D g) {
        this.g = g;
        g.setColor(java.awt.Color.BLUE);
        g.setStroke(new java.awt.BasicStroke(2));
    }

    /**
     * 绘制圆形
     * @param x 圆心x坐标
     * @param y 圆心y坐标 
     * @param radius 半径(必须>0)
     * @throws IllegalArgumentException 如果半径不合法
     */
    @Override
    public void drawCircle(int x, int y, int radius) {
        if (g != null) {
            g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        if (g != null) {
            g.drawRect(x, y, width, height);
        }
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        if (g != null) {
            g.drawOval(x, y, width, height);
        }
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        if (g != null) {
            Polygon triangle = new Polygon();
            triangle.addPoint(x1, y1);
            triangle.addPoint(x2, y2);
            triangle.addPoint(x3, y3);
            g.drawPolygon(triangle);
        }
    }
}


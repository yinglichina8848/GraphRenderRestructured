/**
 * SwingRenderer
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.bridge;

import java.awt.Graphics2D;
import java.awt.Polygon;

public class SwingRenderer implements Renderer {
    private Graphics2D g;

    public void setGraphics(Graphics2D g) {
        this.g = g;
        g.setColor(java.awt.Color.BLUE);
        g.setStroke(new java.awt.BasicStroke(2));
    }

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


/**
 * Ellipse
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;



public class Ellipse implements Shape {
    private int x, y, width, height;

    public Ellipse() {} // 必须要无参构造器

    public Ellipse(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.drawEllipse(x, y, width, height);
    }

    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visitEllipse(this);
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}


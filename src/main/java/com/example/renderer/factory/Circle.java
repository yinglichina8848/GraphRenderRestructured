/**
 * Circle
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;


public class Circle implements Shape {
    private int x, y, radius;

    public Circle() {} // 必须要无参构造器

    public Circle(int x, int y, int radius) {
        this.x = x; this.y = y; this.radius = radius;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.drawCircle(x, y, radius);
    }

    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visitCircle(this);
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getRadius() { return radius; }

    public int getR() {
        return radius;
    }

}


/**
 * Triangle
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.factory;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;



public class Triangle implements Shape {
    private int x1, y1, x2, y2, x3, y3;

    public Triangle() {} // 必须要无参构造器

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
        this.x3 = x3; this.y3 = y3;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.drawTriangle(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visitTriangle(this);
    }

    @Override
    public void move(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
        x3 += dx;
        y3 += dy;
    }

    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    public int getX3() { return x3; }
    public int getY3() { return y3; }
}

package com.example.renderer.factory;

/**
 * ShapeFactory接口的默认实现类
 */
public class ShapeFactoryImpl implements ShapeFactory {
    @Override
    public Circle createCircle(int x, int y, int radius) {
        return new Circle(x, y, radius);
    }

    @Override
    public Rectangle createRectangle(int x, int y, int width, int height) {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Triangle createTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        return new Triangle(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public Ellipse createEllipse(int x, int y, int width, int height) {
        return new Ellipse(x, y, width, height);
    }
}

package com.example.renderer.factory;

import com.example.renderer.factory.Shape;

/**
 * 基础图形工厂实现类，提供标准图形对象的创建。
 * 
 * <p>作为ShapeFactory接口的默认实现，它创建标准的图形实例：
 * <ul>
 *   <li>圆形(Circle)</li>
 *   <li>矩形(Rectangle)</li>
 *   <li>三角形(Triangle)</li>
 *   <li>椭圆(Ellipse)</li>
 * </ul>
 */
public class BasicShapeFactory implements ShapeFactory {
    @Override
    public Circle createCircle(int x, int y, int radius) {
        Shape.validatePosition(x, y);
        if (radius < 0) {
            throw new IllegalArgumentException("半径不能为负数");
        }
        return new Circle(x, y, radius);
    }

    @Override
    public Rectangle createRectangle(int x, int y, int width, int height) {
        Shape.validatePosition(x, y);
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("宽度和高度不能为负数");
        }
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Triangle createTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        Shape.validatePosition(x1, y1);
        Shape.validatePosition(x2, y2);
        Shape.validatePosition(x3, y3);
        return new Triangle(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public Ellipse createEllipse(int x, int y, int width, int height) {
        Shape.validatePosition(x, y);
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("宽度和高度不能为负数");
        }
        return new Ellipse(x, y, width, height);
    }
}

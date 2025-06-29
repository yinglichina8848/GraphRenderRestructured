package com.example.renderer.factory;

/**
 * ShapeFactory接口的默认实现类
 */
public class ShapeFactoryImpl implements ShapeFactory {
    @Override
    public Circle createCircle(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("圆的半径必须为正数 (当前值: " + radius + ")");
        }
        Circle circle = new Circle(x, y, radius);
        System.out.printf("[INFO] 创建圆形 - 位置: (%d,%d), 半径: %d\n", x, y, radius);
        return circle;
    }

    @Override
    public Rectangle createRectangle(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                String.format("矩形的宽高必须为正数 (width=%d, height=%d)", width, height));
        }
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Triangle createTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        ShapeValidator.validatePosition(x1, y1);
        ShapeValidator.validatePosition(x2, y2);
        ShapeValidator.validatePosition(x3, y3);
        System.out.printf("[INFO] 创建三角形 - 顶点: (%d,%d), (%d,%d), (%d,%d)\n", 
            x1, y1, x2, y2, x3, y3);
        return new Triangle(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public Ellipse createEllipse(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("椭圆的宽度和高度必须为正数。 (width=" + width + ", height=" + height + ")");
        }
        return new Ellipse(x, y, width, height);
    }
}

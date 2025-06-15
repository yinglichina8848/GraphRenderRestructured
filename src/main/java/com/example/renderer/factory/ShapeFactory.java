
package com.example.renderer.factory;

/**
 * ShapeFactory接口定义了创建各种图形对象的工厂方法。
 * 使用工厂模式可以解耦图形对象的创建和使用。
 * 
 * @see BasicShapeFactory
 * @see Circle
 * @see Rectangle
 */
public interface ShapeFactory {
    Shape createCircle(int x, int y, int radius);
    Shape createRectangle(int x, int y, int width, int height);
}

class BasicShapeFactory implements ShapeFactory {
    public Shape createCircle(int x, int y, int radius) {
        return new Circle(x, y, radius);
    }
    public Shape createRectangle(int x, int y, int width, int height) {
        return new Rectangle(x, y, width, height);
    }
}

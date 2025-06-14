
package com.example.renderer.factory;


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
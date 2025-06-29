package com.example.renderer.factory;

public class ShapeValidator {
    public static void validatePosition(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("坐标不能为负数");
        }
    }
}

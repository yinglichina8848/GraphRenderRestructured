package com.example.renderer.bridge;

import com.example.renderer.factory.Shape;
public interface ShapeObserver {
    void onShapeChanged(Shape shape);
}
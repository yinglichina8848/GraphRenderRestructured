package com.example.renderer.observer;

import com.example.renderer.factory.Shape;

/**
 * ShapeObserver接口的默认实现类
 */
public class ShapeObserverImpl implements ShapeObserver {
    @Override
    public void onShapeChanged(Shape shape) {
        if (shape != null) {
            System.out.println("Shape changed: " + shape.getClass().getSimpleName());
        }
    }
}

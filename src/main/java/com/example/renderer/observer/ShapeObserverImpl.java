package com.example.renderer.observer;

/**
 * ShapeObserver接口的默认实现类
 */
public class ShapeObserverImpl implements ShapeObserver {
    @Override
    public void onShapeChanged() {
        System.out.println("图形发生变化");
    }
}

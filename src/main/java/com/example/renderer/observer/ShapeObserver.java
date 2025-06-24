package com.example.renderer.observer;

import com.example.renderer.factory.Shape;

/**
 * ShapeObserver接口定义了观察者模式中的观察者角色。
 * 当被观察的图形对象发生变化时，会调用onShapeChanged()方法。
 */
public interface ShapeObserver {
    /**
     * 当被观察的图形发生变化时调用
     */
    void onShapeChanged();
}

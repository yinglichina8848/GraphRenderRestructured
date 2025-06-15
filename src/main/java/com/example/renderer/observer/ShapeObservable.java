
// 6. ShapeObservable.java
package com.example.renderer.observer;

/**
 * ShapeObservable是可观察对象，维护观察者列表并在状态变化时通知观察者。
 * 实现了观察者模式中的主题(Subject)角色。
 * 
 * @see ShapeObserver
 * @author liying
 * @since 2025-06-14
 */

import java.util.ArrayList;
import java.util.List;

public class ShapeObservable {
    private final List<ShapeObserver> observers = new ArrayList<>();

    public void addObserver(ShapeObserver o) {
        observers.add(o);
    }

    public void notifyChange() {
        for (ShapeObserver o : observers) {
            o.onShapeChanged();
        }
    }
}

interface ShapeObserver {
    void onShapeChanged();
}


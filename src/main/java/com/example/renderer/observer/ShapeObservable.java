
// 6. ShapeObservable.java
/**
 * ShapeObservable
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.observer;

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



// 6. ShapeObservable.java
package com.example.renderer.observer;

/**
 * ShapeObservable是可观察对象，维护观察者列表并在状态变化时通知观察者。
 * 实现了观察者模式中的主题(Subject)角色。
 * 
 * <p>主要功能：
 * <ul>
 *   <li>管理观察者列表</li>
 *   <li>提供添加观察者的方法</li>
 *   <li>在状态变化时通知所有观察者</li>
 * </ul>
 * 
 * <p>典型用法：
 * <pre>
 * ShapeObservable observable = new ShapeObservable();
 * observable.addObserver(new ShapeObserver() {
 *     public void onShapeChanged(Shape shape) {
 *         // 处理图形变化
 *     }
 * });
 * observable.notifyChange(); // 通知所有观察者
 * </pre>
 * 
 * @see ShapeObserver 观察者接口
 * @author liying
 * @since 1.0
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



package com.example.renderer.bridge;

import com.example.renderer.factory.Shape;
import com.example.renderer.observer.ShapeObservable;

/**
 * ShapeObserver接口定义了观察者模式中的观察者角色。
 * 当被观察的图形对象发生变化时，会调用onShapeChanged()方法。
 * 
 * @see ShapeObservable
 */
/**
 * ShapeObserver接口定义了观察者模式中的观察者角色。
 * 当被观察的图形对象发生变化时，会调用onShapeChanged()方法。
 * 
 * <p>典型用法：
 * <pre>
 * shapeObservable.addObserver(new ShapeObserver() {
 *     public void onShapeChanged(Shape shape) {
 *         // 处理图形变化
 *     }
 * });
 * </pre>
 * </p>
 * 
 * @see ShapeObservable 可观察对象
 * @see Shape 图形接口
 * @author liying
 * @since 1.0
 */
public interface ShapeObserver {
    /**
     * 当被观察的图形发生变化时调用
     * @param shape 发生变化的图形对象
     */
    void onShapeChanged(Shape shape);
}

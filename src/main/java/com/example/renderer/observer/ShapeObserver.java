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
 * <pre>{@code
 * shapeObservable.addObserver(new ShapeObserver() {
 *     public void onShapeChanged(Shape shape) {
 *         // 处理图形变化
 *     }
 * });
 * }</pre>
 *
 * <p>设计考虑：
 * <ul>
 *   <li>松耦合 - 观察者与被观察者之间没有直接依赖</li>
 *   <li>支持多个观察者</li>
 *   <li>事件驱动 - 只在变化时通知</li>
 * </ul>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @see ShapeObservable 可观察对象
 * @see Shape 图形接口
 * @since 2025-06-24
 */
public interface ShapeObserver {
    /**
     * 当被观察的图形发生变化时调用
     * @param shape 发生变化的图形对象
     */
    void onShapeChanged(Shape shape);
}

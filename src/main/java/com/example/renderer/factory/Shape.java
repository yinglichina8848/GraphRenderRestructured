
package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;

/**
 * Shape接口定义了所有图形对象的基本行为。
 * 作为图形系统的核心接口，它支持渲染、移动和访问者模式操作。
 * 所有具体图形类(如圆形、矩形等)都应实现此接口。
 */



public interface Shape {
    /**
     * 使用指定的渲染器绘制图形
     * @param renderer 用于绘制图形的渲染器实现
     */
    void render(Renderer renderer);

    /**
     * 移动图形的位置
     * @param dx X轴方向的移动距离
     * @param dy Y轴方向的移动距离
     */
    void move(int dx, int dy);

    /**
     * 接受访问者访问，实现访问者模式
     * @param visitor 用于处理图形的访问者对象
     */
    void accept(ExportVisitor visitor);
}

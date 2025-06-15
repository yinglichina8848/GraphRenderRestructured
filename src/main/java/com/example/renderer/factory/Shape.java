
package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;

/**
 * Shape接口定义了所有图形对象的基本行为。
 * 作为图形系统的核心接口，它支持渲染、移动和访问者模式操作。
 * 所有具体图形类(如圆形、矩形等)都应实现此接口。
 */



public interface Shape {
    void render(Renderer renderer);
    void move(int dx, int dy);
    void accept(ExportVisitor visitor);
}


package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;



public interface Shape {
    void render(Renderer renderer);
    void move(int dx, int dy);
    void accept(ExportVisitor visitor);
}
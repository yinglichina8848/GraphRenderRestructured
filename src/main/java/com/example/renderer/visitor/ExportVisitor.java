package com.example.renderer.visitor;

import com.example.renderer.factory.Circle;
import com.example.renderer.factory.Rectangle;
import com.example.renderer.factory.Ellipse;
import com.example.renderer.factory.Triangle;

public interface ExportVisitor {
    void visitCircle(Circle circle);
    void visitRectangle(Rectangle rectangle);
    void visitEllipse(Ellipse ellipse);
    void visitTriangle(Triangle triangle);
}



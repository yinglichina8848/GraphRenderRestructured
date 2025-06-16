package com.example.renderer.visitor;

import com.example.renderer.factory.Circle;
import com.example.renderer.factory.Rectangle;
import com.example.renderer.factory.Ellipse;
import com.example.renderer.factory.Triangle;

/**
 * ExportVisitor接口定义了访问者模式中的访问者角色。
 * 每个visit方法对应一种图形类型的导出逻辑。
 * 
 * @see JSONExportVisitor
 */
/**
 * 访问者接口，用于实现访问者模式
 */
public interface ExportVisitor {
    void visitCircle(Circle circle);
    void visitRectangle(Rectangle rectangle);
    void visitEllipse(Ellipse ellipse);
    void visitTriangle(Triangle triangle);
}



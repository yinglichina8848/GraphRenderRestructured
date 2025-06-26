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
    /**
     * 访问圆形对象
     * @param circle 要处理的圆形对象
     */
    void visitCircle(Circle circle);
    
    /**
     * 访问矩形对象
     * @param rectangle 要处理的矩形对象
     */
    void visitRectangle(Rectangle rectangle);
    
    /**
     * 访问椭圆对象
     * @param ellipse 要处理的椭圆对象
     */
    void visitEllipse(Ellipse ellipse);
    
    /**
     * 访问三角形对象
     * @param triangle 要处理的三角形对象
     */
    void visitTriangle(Triangle triangle);
}



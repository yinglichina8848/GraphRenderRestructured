package com.example.renderer.visitor;

import com.example.renderer.factory.Circle;
import com.example.renderer.factory.Rectangle;
import com.example.renderer.factory.Ellipse;
import com.example.renderer.factory.Triangle;

/**
 * 访问者模式接口，用于实现对图形对象的导出操作。
 * 
 * <p>该接口定义了访问者模式中的访问者角色，每个visit方法对应一种图形类型的导出逻辑。
 * 具体实现类应提供针对不同图形的导出功能。</p>
 * 
 * @see JSONExportVisitor JSON格式导出实现
 * @see XMLExportVisitor XML格式导出实现
 * 
 * @author Aider+SillconFlow-DeepSeek-R1
 * @since 2025-06-29
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



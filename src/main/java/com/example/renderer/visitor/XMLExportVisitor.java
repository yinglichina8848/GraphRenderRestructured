package com.example.renderer.visitor;

import com.example.renderer.factory.*;

/**
 * XML导出访问者实现类
 */
public class XMLExportVisitor implements ExportVisitor {
    @Override
    public void visitCircle(Circle c) {
        if (c.getR() <= 0) {
            throw new IllegalArgumentException("圆的半径必须为正数");
        }
        System.out.printf(
            "<circle>\n" +
            "  <x>%d</x>\n" +   // 圆心X坐标(像素)
            "  <y>%d</y>\n" +   // 圆心Y坐标(像素)
            "  <radius>%d</radius>\n" +  // 圆半径(像素)
            "  <area>%.2f</area>\n" +    // 圆面积(平方像素)
            "</circle>\n",
            c.getX(), c.getY(), c.getR(), Math.PI * c.getR() * c.getR());
    }

    /**
     * 将矩形对象转换为XML格式输出
     * 
     * <p>输出格式: &lt;rectangle x="x" y="y" width="width" height="height"/&gt;</p>
     * 
     * @param r 要导出的矩形对象(不能为null)
     * @throws NullPointerException 如果矩形对象为null
     * @throws IllegalArgumentException 如果矩形宽度或高度为负数
     */
    @Override
    public void visitRectangle(Rectangle r) {
        if (r.getWidth() < 0 || r.getHeight() < 0) {
            throw new IllegalArgumentException("矩形的宽度和高度不能为负数");
        }
        System.out.printf(
            "<rectangle x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\"/>\n",
            r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * 将椭圆对象转换为XML格式输出
     * 
     * <p>输出格式: &lt;ellipse cx="centerX" cy="centerY" rx="xRadius" ry="yRadius"/&gt;</p>
     * 
     * @param e 要导出的椭圆对象(不能为null)
     * @throws NullPointerException 如果椭圆对象为null
     * @throws IllegalArgumentException 如果椭圆宽度或高度为负数
     */
    @Override
    public void visitEllipse(Ellipse e) {
        if (e.getWidth() < 0 || e.getHeight() < 0) {
            throw new IllegalArgumentException("椭圆的宽度和高度不能为负数");
        }
        System.out.printf(
            "<ellipse cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\"/>\n",
            e.getX() + e.getWidth()/2, 
            e.getY() + e.getHeight()/2,
            e.getWidth()/2,
            e.getHeight()/2);
    }

    /**
     * 将三角形对象转换为XML格式输出
     * 
     * <p>输出格式: &lt;polygon points="x1,y1 x2,y2 x3,y3"/&gt;</p>
     * 
     * @param t 要导出的三角形对象(不能为null)
     * @throws NullPointerException 如果三角形对象为null
     */
    @Override
    public void visitTriangle(Triangle t) {
        System.out.printf(
            "<polygon points=\"%d,%d %d,%d %d,%d\"/>\n",
            t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3());
    }
}

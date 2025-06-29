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

    @Override
    public void visitRectangle(Rectangle r) {
        System.out.printf(
            "<rectangle x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\"/>\n",
            r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public void visitEllipse(Ellipse e) {
        System.out.printf(
            "<ellipse cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\"/>\n",
            e.getX() + e.getWidth()/2, 
            e.getY() + e.getHeight()/2,
            e.getWidth()/2,
            e.getHeight()/2);
    }

    @Override
    public void visitTriangle(Triangle t) {
        System.out.printf(
            "<polygon points=\"%d,%d %d,%d %d,%d\"/>\n",
            t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3());
    }
}

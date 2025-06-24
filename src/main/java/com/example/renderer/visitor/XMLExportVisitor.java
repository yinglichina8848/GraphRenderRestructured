package com.example.renderer.visitor;

import com.example.renderer.factory.*;

/**
 * XML导出访问者实现类
 */
public class XMLExportVisitor implements ExportVisitor {
    @Override
    public void visitCircle(Circle c) {
        System.out.printf(
            "<circle x=\"%d\" y=\"%d\" radius=\"%d\"/>\n",
            c.getX(), c.getY(), c.getR());
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
            "<ellipse x=\"%d\" y=\"%d\" rx=\"%d\" ry=\"%d\"/>\n",
            e.getX(), e.getY(), e.getWidth()/2, e.getHeight()/2);
    }

    @Override
    public void visitTriangle(Triangle t) {
        System.out.printf(
            "<polygon points=\"%d,%d %d,%d %d,%d\"/>\n",
            t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3());
    }
}

/**
 * JSONExportVisitor
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.visitor;

import com.example.renderer.factory.Circle;
import com.example.renderer.factory.Rectangle;
import com.example.renderer.factory.Ellipse;
import com.example.renderer.factory.Triangle;

public class JSONExportVisitor implements ExportVisitor {
    @Override
    public void visitCircle(Circle c) {
        System.out.printf("{\"type\":\"circle\", \"x\":%d, \"y\":%d, \"r\":%d}\n", c.getX(), c.getY(), c.getR());
    }

    @Override
    public void visitRectangle(Rectangle r) {
        System.out.printf("{\"type\":\"rectangle\", \"x\":%d, \"y\":%d, \"w\":%d, \"h\":%d}\n", r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public void visitEllipse(Ellipse e) {
        System.out.printf("{\"type\":\"ellipse\", \"x\":%d, \"y\":%d, \"rx\":%d, \"ry\":%d}\n", 
            e.getX(), e.getY(), e.getWidth()/2, e.getHeight()/2);
    }

    @Override
    public void visitTriangle(Triangle t) {
        System.out.printf("{\"type\":\"triangle\", \"x1\":%d, \"y1\":%d, \"x2\":%d, \"y2\":%d, \"x3\":%d, \"y3\":%d}\n",
                t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3());
    }


}


package com.example.renderer.visitor;

/**
 * JSONExportVisitor实现了将图形导出为JSON格式的访问者。
 * 
 * <p>作为访问者模式(Visitor Pattern)的具体实现，它为每种图形类型提供了专门的visit方法，
 * 允许在不修改图形类的情况下添加新的导出操作。</p>
 * 
 * <p>输出格式规范：
 * <ul>
 *   <li>圆形(circle): {"type":"circle", "x":x, "y":y, "r":radius}</li>
 *   <li>矩形(rectangle): {"type":"rectangle", "x":x, "y":y, "w":width, "h":height}</li>
 *   <li>椭圆(ellipse): {"type":"ellipse", "x":x, "y":y, "rx":xRadius, "ry":yRadius}</li>
 *   <li>三角形(triangle): {"type":"triangle", "x1":x1, "y1":y1, ..., "y3":y3}</li>
 * </ul>
 * </p>
 * 
 * <p>使用示例：
 * <pre>{@code
 * Shape circle = new Circle(10, 10, 5);
 * ExportVisitor visitor = new JSONExportVisitor();
 * circle.accept(visitor); // 输出JSON到标准输出
 * }</pre>
 * </p>
 * 
 * <p>设计考虑：
 * <ul>
 *   <li>线程安全：无共享状态，可多线程使用</li>
 *   <li>性能：直接输出到标准输出，避免内存开销</li>
 *   <li>扩展性：新增图形类型只需实现对应visit方法</li>
 * </ul>
 * </p>
 * 
 * @see ExportVisitor 访问者接口
 * @see Shape#accept(ExportVisitor) 接受访问者的入口方法
 * @see <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Visitor Pattern</a>
 * @author liying
 * @since 1.0
 * @version 1.0
 */

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


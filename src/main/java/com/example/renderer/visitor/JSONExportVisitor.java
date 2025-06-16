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
 * 
 * <p>JSON编码说明：
 * <ul>
 *   <li>所有输出使用UTF-8编码</li>
 *   <li>数字值不添加引号</li>
 *   <li>不进行JSON字符串转义（简单实现）</li>
 * </ul>
 * </p>
 * 
 * <p>性能优化建议：
 * <ul>
 *   <li>对于大批量导出，考虑使用StringBuilder或JSON库</li>
 *   <li>需要文件输出时可重定向System.out</li>
 * </ul>
 * </p>
 */

import com.example.renderer.factory.Circle;
import com.example.renderer.factory.Rectangle;
import com.example.renderer.factory.Ellipse;
import com.example.renderer.factory.Triangle;

public class JSONExportVisitor implements ExportVisitor {
    // JSON格式模板常量
    /**
     * 圆形JSON格式模板
     * 格式: {"type":"circle", "x":x, "y":y, "r":radius}
     */
    /**
     * 圆形JSON格式模板
     * 格式: {"type":"circle", "x":x, "y":y, "r":radius}
     */
    private static final String CIRCLE_JSON = 
        "{\"type\":\"circle\", \"x\":%d, \"y\":%d, \"r\":%d}";
    private static final String RECTANGLE_JSON = 
        "{\"type\":\"rectangle\", \"x\":%d, \"y\":%d, \"w\":%d, \"h\":%d}";
    private static final String ELLIPSE_JSON = 
        "{\"type\":\"ellipse\", \"x\":%d, \"y\":%d, \"rx\":%d, \"ry\":%d}";
    private static final String TRIANGLE_JSON = 
        "{\"type\":\"triangle\", \"x1\":%d, \"y1\":%d, \"x2\":%d, \"y2\":%d, \"x3\":%d, \"y3\":%d}";

    /**
     * 将圆形对象转换为JSON格式输出
     * 格式: {"type":"circle", "x":x, "y":y, "r":radius}
     * 
     * @param c 要导出的圆形对象，不能为null
     * @throws NullPointerException 如果参数c为null
     * @throws IllegalArgumentException 如果半径值为负数
     */
    @Override
    public void visitCircle(Circle c) {
        if (c.getR() < 0) {
            throw new IllegalArgumentException("圆的半径不能为负数");
        }
        System.out.printf(CIRCLE_JSON + "\n", c.getX(), c.getY(), c.getR());
    }

    /**
     * 将矩形对象转换为JSON格式输出
     * 
     * @param r 要导出的矩形对象，不能为null
     * @throws NullPointerException 如果参数r为null
     */
    /**
     * 将矩形对象转换为JSON格式输出
     * 格式: {"type":"rectangle", "x":x, "y":y, "w":width, "h":height}
     * 
     * @param r 要导出的矩形对象，不能为null
     * @throws NullPointerException 如果参数r为null
     * @throws IllegalArgumentException 如果宽度或高度为负数
     */
    @Override
    public void visitRectangle(Rectangle r) {
        if (r.getWidth() < 0 || r.getHeight() < 0) {
            throw new IllegalArgumentException("矩形的宽度和高度不能为负数");
        }
        System.out.printf(RECTANGLE_JSON + "\n", r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * 将椭圆对象转换为JSON格式输出
     * 
     * @param e 要导出的椭圆对象，不能为null
     * @throws NullPointerException 如果参数e为null
     */
    /**
     * 将椭圆对象转换为JSON格式输出
     * 格式: {"type":"ellipse", "x":x, "y":y, "rx":xRadius, "ry":yRadius}
     * 
     * @param e 要导出的椭圆对象，不能为null
     * @throws NullPointerException 如果参数e为null
     * @throws IllegalArgumentException 如果宽度或高度为负数
     */
    @Override
    public void visitEllipse(Ellipse e) {
        if (e.getWidth() < 0 || e.getHeight() < 0) {
            throw new IllegalArgumentException("椭圆的宽度和高度不能为负数");
        }
        System.out.printf(ELLIPSE_JSON + "\n", 
            e.getX(), e.getY(), e.getWidth()/2, e.getHeight()/2);
    }

    /**
     * 将三角形对象转换为JSON格式输出
     * 
     * @param t 要导出的三角形对象，不能为null
     * @throws NullPointerException 如果参数t为null
     */
    /**
     * 将三角形对象转换为JSON格式输出
     * 格式: {"type":"triangle", "x1":x1, "y1":y1, ..., "y3":y3}
     * 
     * @param t 要导出的三角形对象，不能为null
     * @throws NullPointerException 如果参数t为null
     */
    @Override
    public void visitTriangle(Triangle t) {
        System.out.printf(TRIANGLE_JSON + "\n",
                t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3());
    }


}


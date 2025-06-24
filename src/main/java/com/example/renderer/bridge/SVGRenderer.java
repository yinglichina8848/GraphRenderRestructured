package com.example.renderer.bridge;

/**
 * SVGRenderer实现了将图形渲染为SVG格式字符串输出到控制台。
 * 
 * <p>作为Renderer接口的实现，它生成标准的SVG元素字符串：
 * <ul>
 *   <li>圆形: &lt;circle&gt; 元素</li>
 *   <li>矩形: &lt;rect&gt; 元素</li>
 *   <li>椭圆: &lt;ellipse&gt; 元素</li>
 *   <li>三角形: &lt;polygon&gt; 元素</li>
 * </ul>
 * 
 * <p>主要用于调试和演示目的，实际应用中可输出到文件或网络。</p>
 * 
 * @author liying
 * @since 1.0
 */
public class SVGRenderer implements Renderer {
    @Override
    public void drawCircle(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive (was " + radius + ")");
        }
        System.out.printf("<circle cx='%d' cy='%d' r='%d' />\n", x, y, radius);
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        System.out.printf("<rect x='%d' y='%d' width='%d' height='%d' />\n", x, y, width, height);
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("<polygon points='%d,%d %d,%d %d,%d' />\n", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        System.out.printf("<ellipse cx='%d' cy='%d' rx='%d' ry='%d' />\n", x, y, width/2, height/2);
    }
}

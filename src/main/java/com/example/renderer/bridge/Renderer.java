package com.example.renderer.bridge;

/**
 * Renderer接口定义了图形渲染的基本操作。
 * 作为桥接模式中的实现部分(Implementor)，它抽象了不同渲染方式的具体实现。
 * 
 * <p>实现类需要提供以下图形的绘制能力：
 * <ul>
 *   <li>圆形</li>
 *   <li>矩形</li> 
 *   <li>椭圆</li>
 *   <li>三角形</li>
 * </ul>
 * 
 * @see SwingRenderer
 * @see SVGRenderer
 */
public interface Renderer {
    void drawCircle(int x, int y, int radius);
    void drawRectangle(int x, int y, int width, int height);
    void drawEllipse(int x, int y, int width, int height);
    /**
     * 绘制三角形
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     */
    void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3);
}

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
 * @see Renderer 渲染器接口
 * @author liying
 * @since 1.0
 */
class SVGRenderer implements Renderer {
    public void drawCircle(int x, int y, int radius) {
        System.out.printf("<circle cx='%d' cy='%d' r='%d' />\n", x, y, radius);
    }
    public void drawRectangle(int x, int y, int width, int height) {
        System.out.printf("<rect x='%d' y='%d' width='%d' height='%d' />\n", x, y, width, height);
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("<polygon points='%d,%d %d,%d %d,%d' />\n", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void drawEllipse(int x, int y, int rx, int ry) {
        System.out.printf("<ellipse cx='%d' cy='%d' rx='%d' ry='%d' />\n", x, y, rx, ry);
    }

}

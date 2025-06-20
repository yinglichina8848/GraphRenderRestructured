package com.example.renderer.bridge;

/**
 * Renderer接口定义了图形渲染的基本操作。
 * 作为桥接模式中的实现部分(Implementor)，它抽象了不同渲染方式的具体实现。
 * 
 * <p>实现类需要提供以下图形的绘制能力：
 * <ul>
 *   <li>圆形 - 通过drawCircle方法</li>
 *   <li>矩形 - 通过drawRectangle方法</li> 
 *   <li>椭圆 - 通过drawEllipse方法</li>
 *   <li>三角形 - 通过drawTriangle方法</li>
 * </ul>
 * 
 * <p>典型用法：
 * <pre>{@code
 * Renderer renderer = new SwingRenderer();
 * renderer.drawCircle(100, 100, 50); // 绘制圆形
 * }</pre>
 *
 * @see SwingRenderer 基于Swing的渲染实现
 * @see SVGRenderer 基于SVG输出的渲染实现
 * @since 1.0
 */
public interface Renderer {
    /**
     * 绘制圆形
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径(必须>0)
     * @throws IllegalArgumentException 如果半径不合法
     */
    void drawCircle(int x, int y, int radius);
    
    /**
     * 绘制矩形
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    void drawRectangle(int x, int y, int width, int height);
    
    /**
     * 绘制椭圆
     * @param x 椭圆外接矩形左上角x坐标
     * @param y 椭圆外接矩形左上角y坐标
     * @param width 椭圆宽度(必须>0)
     * @param height 椭圆高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
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
    /**
     * 绘制圆形并输出SVG格式字符串。
     * 
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径(必须>0)
     * @throws IllegalArgumentException 如果半径不合法
     */
    @Override
    public void drawCircle(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive (was " + radius + ")");
        }
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

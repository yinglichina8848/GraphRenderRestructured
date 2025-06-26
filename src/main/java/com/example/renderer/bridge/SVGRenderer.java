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
import java.util.Objects;
import com.example.renderer.bridge.Renderer;

/**
 * SVG格式渲染器实现
 */
public class SVGRenderer implements Renderer {
    private StringBuilder svgBuilder = new StringBuilder();
    private String strokeColor = "black";
    private String fillColor = "none";
    private int strokeWidth = 1;

    /**
     * 创建SVG渲染器实例
     */
    public SVGRenderer() {
        // 不再自动输出SVG文档头，由调用方控制
    }

    @Override
    public void setStyle(String stroke, String fill, int width) {
        this.strokeColor = Objects.requireNonNull(stroke);
        this.fillColor = Objects.requireNonNull(fill);
        if (width < 0) {
            throw new IllegalArgumentException("线宽不能为负数");
        }
        this.strokeWidth = width;
    }

    @Override
    public Object getContext() {
        return svgBuilder;
    }

    @Override
    public void beginFrame() {
        svgBuilder.append("<svg xmlns='http://www.w3.org/2000/svg'>\n");
    }

    @Override
    public void endFrame() {
        svgBuilder.append("</svg>");
        System.out.println(svgBuilder.toString());
        svgBuilder.setLength(0); // 清空以便下次使用
    }

    /**
     * 获取当前生成的SVG字符串
     * @return 完整的SVG文档字符串
     */
    public String getSVG() {
        return svgBuilder.toString();
    }

    // 移除样式相关代码，保持简单输出格式
    @Override
    public void drawCircle(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("半径必须为正数 (当前值: " + radius + ")");
        }
        svgBuilder.append(String.format("<circle cx='%d' cy='%d' r='%d' />\n", 
            x, y, radius));
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive (width=" + width + ", height=" + height + ")");
        }
        System.out.printf("<rect x='%d' y='%d' width='%d' height='%d' />\n", x, y, width, height);
    }

    /**
     * 绘制三角形(SVG格式)
     * 
     * <p>修改记录：
     * <ul>
     *   <li>2025-06-24 - 初始实现</li>
     *   <li>2025-06-27 - 优化输出格式</li>
     * </ul>
     * 
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     * @since 2025-06-24
     */
    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("<polygon points='%d,%d %d,%d %d,%d' />\n", 
            x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        System.out.printf("<ellipse cx='%d' cy='%d' rx='%d' ry='%d' />\n", x, y, width, height);
    }
}

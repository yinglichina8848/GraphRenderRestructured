/**
 * SVG渲染器实现，将图形输出为SVG格式的XML字符串。
 * 
 * <p>实现了Renderer接口，用于生成SVG格式的图形描述。
 * 每个图形元素都输出为对应的SVG标签。</p>
 * 
 * <p>示例输出：
 * <pre>{@code
 * <circle cx='100' cy='100' r='50' />
 * }</pre>
 * </p>
 * 
 * @see Renderer
 * @author liying
 * @since 1.0
 */
class SVGRenderer implements Renderer {
    /**
     * 绘制圆形，输出为SVG circle标签
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆半径
     */
    @Override
    public void drawCircle(int x, int y, int radius) {
        System.out.printf("<circle cx='%d' cy='%d' r='%d' />\n", x, y, radius);
    }

    /**
     * 绘制矩形，输出为SVG rect标签
     * @param x 矩形左上角x坐标
     * @param y 矩形左上角y坐标
     * @param width 矩形宽度
     * @param height 矩形高度
     */
    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        System.out.printf("<rect x='%d' y='%d' width='%d' height='%d' />\n", x, y, width, height);
    }

    /**
     * 绘制三角形，输出为SVG polygon标签
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     */
    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("<polygon points='%d,%d %d,%d %d,%d' />\n", x1, y1, x2, y2, x3, y3);
    }

    /**
     * 绘制椭圆，输出为SVG ellipse标签
     * @param x 椭圆中心x坐标
     * @param y 椭圆中心y坐标
     * @param width 椭圆x轴半径
     * @param height 椭圆y轴半径
     */
    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        System.out.printf("<ellipse cx='%d' cy='%d' rx='%d' ry='%d' />\n", x, y, width, height);
    }
}

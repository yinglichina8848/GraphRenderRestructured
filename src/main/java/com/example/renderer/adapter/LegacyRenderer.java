/**
 * LegacyRenderer
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.legacy;

/**
 * LegacyRenderer是一个遗留的图形渲染类，提供专有的图形绘制方法。
 * 
 * <p>该类不符合新的Renderer接口规范，需要通过适配器模式适配到新系统。
 * 提供以下图形绘制方法：
 * <ul>
 *   <li>drawLegacyCircle(): 绘制圆形</li>
 *   <li>drawLegacyRectangle(): 绘制矩形</li>
 *   <li>drawLegacyTriangle(): 绘制三角形</li>
 *   <li>drawLegacyEllipse(): 绘制椭圆</li>
 * </ul>
 * 
 * <p>典型用法：
 * <pre>
 * LegacyRenderer legacy = new LegacyRenderer();
 * Renderer adapter = new LegacyRendererAdapter(legacy);
 * adapter.drawCircle(10,10,5); // 通过适配器调用
 * </pre>
 * 
 * @see com.example.renderer.adapter.LegacyRendererAdapter 适配器实现
 * @see com.example.renderer.bridge.Renderer 新渲染器接口
 * @author liying
 * @since 1.0
 */
public class LegacyRenderer {
    public void drawLegacyCircle(int x, int y, int radius) {
        System.out.printf("LegacyRenderer: 绘制圆形，中心(%d,%d)，半径 %d\n", x, y, radius);
    }

    /**
     * 绘制矩形，使用LegacyRenderer的绘制方法。
     * 
     * @param x      矩形左上角的x坐标
     * @param y      矩形左上角的y坐标
     * @param width  矩形的宽度
     * @param height 矩形的高度
     * 
     * 修改记录:
     * <li>2025-06-19, Aider + Qwen3-8B: 添加注释</li>
     */
    public void drawLegacyRectangle(int x, int y, int width, int height) {
        System.out.printf("LegacyRenderer: 绘制矩形，左上角(%d,%d)，宽 %d，高 %d\n", x, y, width, height);
    }

    /**
     * 绘制三角形，使用LegacyRenderer的绘制方法。
     * 
     * @param x1 三角形第一个顶点的x坐标
     * @param y1 三角形第一个顶点的y坐标
     * @param x2 三角形第二个顶点的x坐标
     * @param y2 三角形第二个顶点的y坐标
     * @param x3 三角形第三个顶点的x坐标
     * @param y3 三角形第三个顶点的y坐标
     * 
     * 修改记录:
     * <li>2025-06-19, Aider + Qwen3-8B: 添加注释</li>
     */
    /**
     * 绘制三角形，使用LegacyRenderer的绘制方法。
     * 
     * @param x1 三角形第一个顶点的x坐标
     * @param y1 三角形第一个顶点的y坐标
     * @param x2 三角形第二个顶点的x坐标
     * @param y2 三角形第二个顶点的y坐标
     * @param x3 三角形第三个顶点的x坐标
     * @param y3 三角形第三个顶点的y坐标
     * @throws IllegalArgumentException 如果参数无效（例如坐标超出范围等）但这里不检查，因为原方法没有抛出
     * 
     * 修改记录：
     * - Aider + Qwen3-8B: 2025-06-19 初始化注释
     */
    public void drawLegacyTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("LegacyRenderer: 绘制三角形，顶点(%d,%d),(%d,%d),(%d,%d)\n", x1, y1, x2, y2, x3, y3);
    }

    /**
     * 绘制椭圆，使用LegacyRenderer的绘制方法。
     * 
     * @param x      椭圆中心的x坐标
     * @param y      椭圆中心的y坐标 
     * @param rx     椭圆X轴半径(必须>0)
     * @param ry     椭圆Y轴半径(必须>0)
     * @throws IllegalArgumentException 如果半径不合法
     * 
     * 修改记录：
     * - Aider + Qwen3-8B: 2025-06-19 初始化注释
     */
    public void drawLegacyEllipse(int x, int y, int rx, int ry) {
        System.out.printf("LegacyRenderer: 绘制椭圆，中心(%d,%d)，X半轴 %d，Y半轴 %d\n", x, y, rx, ry);
    }
}

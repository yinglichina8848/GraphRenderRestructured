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

    public void drawLegacyRectangle(int x, int y, int width, int height) {
        System.out.printf("LegacyRenderer: 绘制矩形，左上角(%d,%d)，宽 %d，高 %d\n", x, y, width, height);
    }

    public void drawLegacyTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("LegacyRenderer: 绘制三角形，顶点(%d,%d),(%d,%d),(%d,%d)\n", x1, y1, x2, y2, x3, y3);
    }

    public void drawLegacyEllipse(int x, int y, int rx, int ry) {
        System.out.printf("LegacyRenderer: 绘制椭圆，中心(%d,%d)，X半轴 %d，Y半轴 %d\n", x, y, rx, ry);
    }
}

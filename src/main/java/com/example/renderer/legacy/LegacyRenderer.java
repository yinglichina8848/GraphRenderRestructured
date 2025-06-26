package com.example.renderer.legacy;

/**
 * 旧版渲染器接口，定义了旧版系统的绘图方法。
 * 新系统通过LegacyRendererAdapter适配此接口。
 */
public interface LegacyRenderer {
    /**
     * 绘制圆形(旧版)
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径
     */
    void drawLegacyCircle(int x, int y, int radius);
    
    /**
     * 绘制矩形(旧版)
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度
     * @param height 矩形高度
     */
    void drawLegacyRectangle(int x, int y, int width, int height);
    
    /**
     * 绘制三角形(旧版)
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     */
    void drawLegacyTriangle(int x1, int y1, int x2, int y2, int x3, int y3);
    
    /**
     * 绘制椭圆(旧版)
     * @param x 椭圆中心x坐标
     * @param y 椭圆中心y坐标
     * @param rx x轴半径
     * @param ry y轴半径
     */
    void drawLegacyEllipse(int x, int y, int rx, int ry);
}

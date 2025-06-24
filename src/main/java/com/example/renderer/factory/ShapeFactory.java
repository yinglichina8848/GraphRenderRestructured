
package com.example.renderer.factory;

/**
 * 图形工厂接口，用于创建各种图形对象。
 */
public interface ShapeFactory {
    /**
     * 创建圆形实例
     * @param x 圆心x坐标
     * @param y 圆心y坐标 
     * @param radius 半径(必须>0)
     * @return 新创建的圆形实例
     * @throws IllegalArgumentException 如果半径不合法
     */
    Circle createCircle(int x, int y, int radius);
    
    /**
     * 创建矩形实例
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * @return 新创建的矩形实例
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    Rectangle createRectangle(int x, int y, int width, int height);
    
    /**
     * 创建三角形实例
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     * @return 新创建的三角形实例
     */
    Triangle createTriangle(int x1, int y1, int x2, int y2, int x3, int y3);
    
    /**
     * 创建椭圆实例
     * @param x 椭圆中心x坐标
     * @param y 椭圆中心y坐标
     * @param width 椭圆宽度(必须>0)
     * @param height 椭圆高度(必须>0)
     * @return 新创建的椭圆实例
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    Ellipse createEllipse(int x, int y, int width, int height);
}


package com.example.renderer.factory;

/**
 * 图形工厂接口，定义了创建各种图形对象的工厂方法。
 * 
 * <p>使用工厂模式可以：
 * <ul>
 *   <li>解耦图形对象的创建和使用</li>
 *   <li>集中管理对象的创建逻辑</li>
 *   <li>便于扩展新的图形类型</li>
 * </ul>
 * </p>
 * 
 * @see BasicShapeFactory 基础实现
 * @see Circle 圆形
 * @see Rectangle 矩形
 * @see Ellipse 椭圆
 * @see Triangle 三角形
 * @author liying
 * @since 1.0
 */
public interface ShapeFactory {
    Shape createCircle(int x, int y, int radius);
    /**
     * 创建矩形实例
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * @return 新创建的矩形实例
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    Shape createRectangle(int x, int y, int width, int height);
}

/**
 * 基础图形工厂实现，提供标准图形对象的创建。
 * 
 * <p>实现了ShapeFactory接口，创建标准的Circle和Rectangle实例。</p>
 * 
 * @see ShapeFactory
 * @author liying
 * @since 1.0
 */
class BasicShapeFactory implements ShapeFactory {
    /**
     * 创建圆形实例
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 半径(必须>0)
     * @return 新创建的圆形实例
     * @throws IllegalArgumentException 如果半径不合法
     */
    public Shape createCircle(int x, int y, int radius) {
        return new Circle(x, y, radius);
    }
    public Shape createRectangle(int x, int y, int width, int height) {
        return new Rectangle(x, y, width, height);
    }
}

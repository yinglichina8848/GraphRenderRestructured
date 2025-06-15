/**
 * 表示椭圆图形的实现类。
 * 
 * <p>椭圆由中心坐标(x,y)和宽度(width)、高度(height)定义。
 * 该类实现了Shape接口，支持基本的渲染、移动和访问者模式操作。
 * 
 * <p>遵循的设计模式：
 * <ul>
 *   <li>桥接模式 - 通过Renderer接口实现绘制逻辑</li>
 *   <li>访问者模式 - 通过accept方法支持扩展操作</li>
 *   <li>命令模式 - move方法支持位置变更</li>
 * </ul>
 * 
 * @author liying
 * @since 2025-06-14
 * @version 1.0
 */
package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;



public class Ellipse implements Shape {
    private int x, y, width, height;

    /**
     * 无参构造器，用于序列化和反射创建实例。
     * 创建后需要通过setter方法设置属性。
     */
    public Ellipse() {}

    /**
     * 构造指定位置和大小的椭圆
     * @param x 椭圆中心的X坐标
     * @param y 椭圆中心的Y坐标
     * @param width 椭圆的宽度
     * @param height 椭圆的高度
     */
    public Ellipse(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    /**
     * 使用指定渲染器绘制椭圆
     * @param renderer 用于绘制椭圆的渲染器实现
     * @throws NullPointerException 如果renderer参数为null
     */
    @Override
    public void render(Renderer renderer) {
        renderer.drawEllipse(x, y, width, height);
    }

    /**
     * 接受访问者访问此椭圆对象
     * @param visitor 用于处理椭圆的访问者对象
     * @throws NullPointerException 如果visitor参数为null
     */
    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visitEllipse(this);
    }

    /**
     * 移动椭圆的位置
     * @param dx X轴方向的移动距离（像素）
     * @param dy Y轴方向的移动距离（像素）
     */
    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * 获取椭圆中心X坐标
     * @return 椭圆中心的X坐标值
     */
    public int getX() { return x; }
    
    /**
     * 获取椭圆中心Y坐标
     * @return 椭圆中心的Y坐标值
     */
    public int getY() { return y; }
    
    /**
     * 获取椭圆宽度
     * @return 椭圆的宽度值
     */
    public int getWidth() { return width; }
    
    /**
     * 获取椭圆高度
     * @return 椭圆的高度值
     */
    public int getHeight() { return height; }
}


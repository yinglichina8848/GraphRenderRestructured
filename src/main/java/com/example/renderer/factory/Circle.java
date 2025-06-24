/**
 * Circle 类实现了圆形图形的具体表示和操作。
 * 
 * <p>作为 Shape 接口的实现类，它提供了圆形的核心功能：
 * <ul>
 *   <li>存储圆心坐标(x,y)和半径(radius)</li>
 *   <li>通过 render() 方法使用指定渲染器绘制圆形</li>
 *   <li>支持通过 move() 方法移动圆形位置</li>
 *   <li>支持通过 accept() 方法接受访问者操作</li>
 * </ul>
 * 
 * <p>构造器参数验证：
 * <ul>
 *   <li>半径必须为正数，否则抛出 IllegalArgumentException</li>
 * </ul>
 * 
 * <p>典型用法：
 * <pre>{@code
 * Circle circle = new Circle(100, 100, 50); // 创建圆形
 * circle.render(renderer); // 绘制圆形
 * circle.move(10, 10); // 移动圆形
 * }</pre>
 * 
 * @author DeepSeek-Coder
 * @version 1.0
 * @see Shape 图形接口
 * @see SwingRenderer Swing渲染实现
 * @since 2025-06-24
 */
package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;


/**
 * 圆形图形实现类，表示一个由圆心坐标和半径定义的圆形。
 * 
 * <p>实现了Shape接口的所有方法：
 * <ul>
 *   <li>render(): 使用渲染器绘制圆形</li>
 *   <li>move(): 移动圆形位置</li>
 *   <li>accept(): 接受访问者访问</li>
 * </ul>
 * 
 * @see Shape 图形接口
 * @author liying
 * @since 1.0
 */
public class Circle implements Shape {
    private int x, y, radius;

    /**
     * 无参构造器，用于序列化和反射创建实例。
     * 创建后需要通过setter方法设置属性。
     * 
     * <p>注意：使用此构造器创建的圆形需要手动设置坐标和半径，
     * 否则可能导致渲染异常。
     */
    /**
     * 无参构造器，用于序列化和反射创建实例。
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public Circle() {}

    /**
     * 创建圆形实例并初始化属性。
     * 
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径(必须>0)
     * @throws IllegalArgumentException 如果半径不是正数
     */
    public Circle(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive (was " + radius + ")");
        }
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.drawCircle(x, y, radius);
    }

    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visitCircle(this);
    }

    /**
     * 移动圆形位置。
     * 
     * @param dx X轴方向的移动距离（像素）
     * @param dy Y轴方向的移动距离（像素）
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * 获取圆心x坐标。
     * 
     * @return 圆心x坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getX() { return x; }

    /**
     * 获取圆心y坐标。
     * 
     * @return 圆心y坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getY() { return y; }

    /**
     * 获取圆形半径。
     * 
     * @return 圆形半径值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getRadius() { return radius; }

    /**
     * 获取圆形半径(简写方法)。
     * 
     * @return 圆形半径值
     * @see #getRadius() 等效方法
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getR() {
        return radius;
    }

}


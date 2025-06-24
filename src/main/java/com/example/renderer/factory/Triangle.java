/**
 * 三角形图形实现类。
 * 
 * <p>表示一个由三个顶点定义的三角形图形，包含以下属性：
 * <ul>
 *   <li>(x1,y1): 第一个顶点坐标</li>
 *   <li>(x2,y2): 第二个顶点坐标</li>
 *   <li>(x3,y3): 第三个顶点坐标</li>
 * </ul>
 * 
 * <p>实现了Shape接口的所有方法：
 * <ul>
 *   <li>render(): 使用渲染器绘制三角形</li>
 *   <li>move(): 移动所有顶点</li>
 *   <li>accept(): 访问者模式支持</li>
 * </ul>
 *
 * <p>典型用法：
 * <pre>{@code
 * Triangle triangle = new Triangle(10,10, 50,10, 30,40);
 * triangle.render(renderer); // 绘制三角形
 * triangle.move(5,5); // 移动三角形
 * }</pre>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @see Shape 图形接口
 * @since 2025-06-24
 */
package com.example.renderer.factory;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;



/**
 * 三角形图形实现类
 */
public class Triangle implements Shape {
    private int x1, y1, x2, y2, x3, y3;

    /**
     * 无参构造器，用于序列化和反射创建实例。
     * 创建后需要通过setter方法设置顶点坐标。
     */
    /**
     * 无参构造器，用于序列化和反射创建实例。
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public Triangle() {}

    /**
     * 创建指定顶点坐标的三角形实例。
     * 
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
        this.x3 = x3; this.y3 = y3;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.drawTriangle(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visitTriangle(this);
    }

    @Override
    public void move(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
        x3 += dx;
        y3 += dy;
    }

    /**
     * 获取第一个顶点的x坐标
     * @return 第一个顶点的x坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getX1() { return x1; }

    /**
     * 获取第一个顶点的y坐标
     * @return 第一个顶点的y坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getY1() { return y1; }

    /**
     * 获取第二个顶点的x坐标
     * @return 第二个顶点的x坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getX2() { return x2; }

    /**
     * 获取第二个顶点的y坐标
     * @return 第二个顶点的y坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getY2() { return y2; }

    /**
     * 获取第三个顶点的x坐标
     * @return 第三个顶点的x坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getX3() { return x3; }

    /**
     * 获取第三个顶点的y坐标
     * @return 第三个顶点的y坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getY3() { return y3; }
}

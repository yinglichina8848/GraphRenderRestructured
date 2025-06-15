/**
 * 矩形图形实现类。
 * 
 * <p>表示一个矩形图形，包含以下属性：
 * <ul>
 *   <li>x,y: 左上角坐标</li>
 *   <li>width: 宽度(必须>0)</li>
 *   <li>height: 高度(必须>0)</li>
 * </ul>
 * 
 * <p>实现了Shape接口的所有方法，包括：
 * <ul>
 *   <li>render(): 使用渲染器绘制矩形</li>
 *   <li>move(): 移动矩形位置</li>
 *   <li>accept(): 访问者模式支持</li>
 * </ul>
 * 
 * @see Shape 图形接口
 * @author liying
 * @since 1.0
 */
package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;



public class Rectangle implements Shape {
    private int x, y, width, height;

    public Rectangle() {} // 必须要无参构造器

    public Rectangle(int x, int y, int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive (was " + width + ")");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive (was " + height + ")");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move(int dx, int dy) {
        try {
            this.x = Math.addExact(this.x, dx);
            this.y = Math.addExact(this.y, dy);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Move operation would cause integer overflow");
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.drawRectangle(x, y, width, height);
    }

    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visitRectangle(this);
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /** 获取矩形左上角x坐标 */
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

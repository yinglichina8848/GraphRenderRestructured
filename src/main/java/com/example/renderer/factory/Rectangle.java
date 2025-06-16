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

    /**
     * Default constructor required for serialization.
     */
    public Rectangle() {} // 必须要无参构造器

    /**
     * Creates a new Rectangle with specified dimensions.
     * @param x the x coordinate of top-left corner
     * @param y the y coordinate of top-left corner
     * @param width the width of rectangle (must be > 0)
     * @param height the height of rectangle (must be > 0)
     * @throws IllegalArgumentException 如果宽度或高度不合法 (&lt;=0)
     */
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
    public void render(Renderer renderer) {
        renderer.drawRectangle(x, y, width, height);
    }

    @Override
    public void accept(ExportVisitor visitor) {
        visitor.visitRectangle(this);
    }

    @Override
    public void move(int dx, int dy) {
        // 处理x坐标移动，防止溢出
        long newX = (long)x + dx;
        x = (int)Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, newX));
        
        // 处理y坐标移动，防止溢出
        long newY = (long)y + dy;
        y = (int)Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, newY));
    }

    /** 获取矩形左上角x坐标 */
    /** 
     * 获取矩形左上角X坐标
     * @return 矩形左上角的X坐标值
     */
    public int getX() { return x; }
    
    /**
     * 获取矩形左上角Y坐标
     * @return 矩形左上角的Y坐标值
     */
    public int getY() { return y; }
    
    /**
     * 获取矩形宽度
     * @return 矩形的宽度值
     */
    public int getWidth() { return width; }
    
    /**
     * 获取矩形高度
     * @return 矩形的高度值
     */
    public int getHeight() { return height; }
}

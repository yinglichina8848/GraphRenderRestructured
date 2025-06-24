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
     * 无参构造器，用于序列化和反射创建实例。
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
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
    /**
     * 创建指定尺寸和位置的新矩形实例。
     * <p>对宽度和高度进行合法性检查，确保其大于0。
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * <p>
     * 修改记录:
     *   - 对方法进行注释
     * 作者: Aider + Qwen3-8B
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

    /**
     * 使用指定渲染器绘制矩形。
     * 
     * @param renderer 用于绘制图形的渲染器实现(非null)
     * @throws NullPointerException 如果renderer参数为null
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
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

    /**
     * 获取矩形左上角x坐标。
     * 
     * @return 矩形左上角x坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getX() { return x; }
    
    /**
     * 获取矩形左上角y坐标。
     * 
     * @return 矩形左上角y坐标值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getY() { return y; }
    
    /**
     * 获取矩形宽度。
     * 
     * @return 矩形宽度值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getWidth() { return width; }
    
    /**
     * 获取矩形高度。
     * 
     * @return 矩形高度值
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public int getHeight() { return height; }
}

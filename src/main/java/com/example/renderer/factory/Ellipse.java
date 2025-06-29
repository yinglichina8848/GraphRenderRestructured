/**
 * 椭圆图形实现类。
 * 
 * <p>表示一个由中心坐标(x,y)和宽度(width)、高度(height)定义的椭圆。
 * 实现了Shape接口，支持以下操作：
 * <ul>
 *   <li>渲染 - 通过render()方法使用指定渲染器绘制</li>
 *   <li>移动 - 通过move()方法改变位置</li>
 *   <li>访问者模式 - 通过accept()方法支持扩展操作</li>
 * </ul>
 *
 * <p>典型用法：
 * <pre>{@code
 * Ellipse ellipse = new Ellipse(100, 100, 80, 40);
 * ellipse.render(renderer); // 绘制椭圆
 * ellipse.move(10, 5); // 移动椭圆
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
 * 椭圆图形实现类
 * 
 * <p>表示一个由中心坐标(x,y)和宽度(width)、高度(height)定义的椭圆。
 * 实现了Shape接口，支持以下操作：
 * <ul>
 *   <li>渲染 - 通过render()方法使用指定渲染器绘制</li>
 *   <li>移动 - 通过move()方法改变位置</li>
 *   <li>访问者模式 - 通过accept()方法支持扩展操作</li>
 * </ul>
 *
 * <p>典型用法：
 * <pre>{@code
 * Ellipse ellipse = new Ellipse(100, 100, 80, 40);
 * ellipse.render(renderer); // 绘制椭圆
 * ellipse.move(10, 5); // 移动椭圆
 * }</pre>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @author Aider+SillconFlow-DeepSeek
 * @since 2025-06-24
 */
public class Ellipse implements Shape {
    private int x, y, width, height;

    /**
     * 无参构造器，用于序列化和反射创建实例。
     * 创建后需要通过setter方法设置属性。
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public Ellipse() {}

    /**
     * 构造指定位置和大小的椭圆
     * @param x 椭圆中心的X坐标
     * @param y 椭圆中心的Y坐标
     * @param width 椭圆的宽度
     * @param height 椭圆的高度
     */
    /**
     * 构造指定位置和大小的椭圆
     * 
     * <p>此构造器创建一个中心在(x,y)处，宽度为width，高度为height的椭圆。</p>
     * 
     * @param x 椭圆中心的X坐标
     * @param y 椭圆中心的Y坐标
     * @param width 椭圆的宽度
     * @param height 椭圆的高度
     * 
     * @author DeepSeek-Coder
     * @since 2025-06-29
     */
    public Ellipse(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    /**
     * 使用指定渲染器绘制椭圆。
     * 
     * <p>实现细节：
     * <ul>
     *   <li>调用renderer.drawEllipse()方法进行实际绘制</li>
     *   <li>传递椭圆的中心坐标和尺寸参数</li>
     *   <li>不处理渲染器抛出的异常，由调用方处理</li>
     * </ul>
     * 
     * @param renderer 用于绘制椭圆的渲染器实现(非null)
     * @throws NullPointerException 如果renderer参数为null
     * @see Renderer#drawEllipse(int, int, int, int)
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
     * 移动椭圆的位置。
     * 
     * <p>实现细节：
     * <ul>
     *   <li>直接修改椭圆的中心坐标</li>
     *   <li>不检查坐标溢出，由调用方确保参数合理</li>
     *   <li>支持负值移动(向左/上移动)</li>
     * </ul>
     * 
     * @param dx X轴方向的移动距离（像素）
     * @param dy Y轴方向的移动距离（像素）
     * @see Shape#move(int, int)
     */
    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * 获取椭圆中心X坐标。
     * 
     * <p>注意：返回的是椭圆的中心点X坐标，不是外接矩形的左上角X坐标。
     * 
     * @return 椭圆中心的X坐标值
     * @see #getY() 获取Y坐标
     * @see #getWidth() 获取宽度
     */
    /**
     * 获取椭圆中心X坐标。
     * 
     * @return 椭圆中心的X坐标值（像素）
     * @author Aider+DeepSeek
     * @since 2025-06-24
     * @author Aider+SillconFlow-DeepSeek-R1
     * @since 2025-06-29
     */
    public int getX() { return x; }
    
    /**
     * 获取椭圆中心Y坐标。
     * 
     * @return 椭圆中心的Y坐标值（像素）
     * @author Aider+DeepSeek
     * @since 2025-06-24
     * @author Aider+SillconFlow-DeepSeek-R1
     * @since 2025-06-29
     */
    public int getY() { return y; }
    
    /**
     * 获取椭圆宽度。
     * 
     * <p>宽度是椭圆在X轴方向的直径长度。</p>
     * 
     * @return 椭圆的宽度值（像素）
     * 
     * @author Aider+SillconFlow-DeepSeek-R1
     * @since 2025-06-29
     */
    public int getWidth() { return width; }
    
    /**
     * 获取椭圆高度。
     * 
     * <p>高度是椭圆在Y轴方向的直径长度。</p>
     * 
     * @return 椭圆的高度值（像素）
     * @see #getWidth() 获取宽度
     * 
     * @author Aider+SillconFlow-DeepSeek-R1
     * @since 2025-06-29
     */
    public int getHeight() { return height; }

    /**
     * 创建并返回当前椭圆的一个深拷贝
     * 
     * <p>此方法通过调用Object.clone()实现拷贝, 适用于只包含基本类型字段的对象。</p>
     * 
     * @return 椭圆的新副本
     * @author Aider+SillconFlow-DeepSeek
     * @since 2025-06-24
     */
    @Override
    public Ellipse clone() {
        try {
            return (Ellipse) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}



package com.example.renderer.factory;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;

/**
 * Shape接口定义了所有图形对象的基本行为。
 * 作为图形系统的核心接口，它支持以下操作：
 * <ul>
 *   <li>渲染 - 通过render(Renderer)方法</li>
 *   <li>移动 - 通过move(int, int)方法</li>
 *   <li>访问者模式 - 通过accept(ExportVisitor)方法</li>
 * </ul>
 * 所有具体图形类(如圆形、矩形等)都应实现此接口。
 * 
 * <p>该接口遵循以下设计模式：
 * <ul>
 *   <li>桥接模式 - 通过Renderer参数实现绘制逻辑的解耦</li>
 *   <li>访问者模式 - 通过accept方法支持对图形的扩展操作</li>
 *   <li>命令模式 - move方法支持图形位置的变更操作</li>
 * </ul>
 *
 * <p>典型实现示例：
 * <pre>{@code
 * public class Circle implements Shape {
 *     public void render(Renderer r) {
 *         r.drawCircle(x, y, radius);
 *     }
 *     // 其他方法实现...
 * }
 * }</pre>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @see Circle 圆形实现
 * @see Rectangle 矩形实现 
 * @see Ellipse 椭圆实现
 * @see Triangle 三角形实现
 * @since 2025-06-24
 */



public interface Shape {
    /**
     * 使用指定的渲染器绘制图形。
     * 具体绘制逻辑由实现类决定，渲染器参数提供实际的绘制能力。
     *
     * @param renderer 用于绘制图形的渲染器实现，不能为null
     * @throws NullPointerException 如果renderer参数为null
     */
    /**
     * 使用指定的渲染器绘制图形。
     * 
     * <p>实现类应确保：
     * <ul>
     *   <li>参数renderer不为null</li>
     *   <li>正确调用renderer的对应绘制方法</li>
     *   <li>处理渲染器抛出的异常</li>
     * </ul>
     * 
     * @param renderer 用于绘制图形的渲染器实现
     * @throws NullPointerException 如果renderer为null
     */
    /**
     * 使用指定的渲染器绘制图形。
     * 
     * @param renderer 用于绘制图形的渲染器实现(非null)
     * @throws NullPointerException 如果renderer参数为null
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    void render(Renderer renderer);

    /**
     * 移动图形的位置。
     * 根据给定的偏移量调整图形的坐标位置，正数表示向右/下移动，负数表示向左/上移动。
     *
     * @param dx X轴方向的移动距离（像素）
     * @param dy Y轴方向的移动距离（像素）
     */
    /**
     * 移动图形的位置。
     * 
     * @param dx X轴方向的移动距离（像素）
     * @param dy Y轴方向的移动距离（像素）
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    void move(int dx, int dy);

    /**
     * 接受访问者访问，实现访问者模式。
     * 允许外部访问者对图形对象进行操作，而不需要修改图形类本身。
     *
     * @param visitor 用于处理图形的访问者对象，不能为null
     * @throws NullPointerException 如果visitor参数为null
     */
    /**
     * 接受访问者访问此图形对象。
     * 
     * @param visitor 用于处理图形的访问者对象(非null)
     * @throws NullPointerException 如果visitor参数为null
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    /**
     * 接受访问者访问此图形对象。
     * 
     * @param visitor 用于处理图形的访问者对象(非null)
     * @throws NullPointerException 如果visitor参数为null
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    void accept(ExportVisitor visitor);
}

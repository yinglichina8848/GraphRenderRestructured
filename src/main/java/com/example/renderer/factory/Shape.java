
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
     * 
     * <p>具体绘制逻辑由实现类决定，渲染器参数提供实际的绘制能力。</p>
     * 
     * <p>实现类应确保：
     * <ul>
     *   <li>参数renderer不为null</li>
     *   <li>正确调用renderer的对应绘制方法</li>
     *   <li>处理渲染器抛出的异常</li>
     * </ul>
     * 
     * @param renderer 用于绘制图形的渲染器实现(非null)
     * @throws NullPointerException 如果渲染器参数为null
     * @throws IllegalArgumentException 如果当前图形的参数不合法(如负尺寸)
     * @throws IllegalStateException 如果图形处于不可渲染的状态
     * 
     * @author Aider+SillconFlow-DeepSeek-R1
     * @since 2025-06-29
     */
    void render(Renderer renderer) throws NullPointerException, IllegalArgumentException, IllegalStateException;

    /**
     * 创建并返回当前图形的一个深拷贝。
     * 
     * <p>此方法返回图形对象的精确副本，包括所有内部状态数据。</p>
     * <p>对于包含引用类型字段的对象，需要递归实现深拷贝。</p>
     * 
     * <p>实现要求：
     * <ul>
     *   <li>必须返回独立的新对象</li>
     *   <li>修改副本不应影响原对象</li>
     *   <li>字段值为引用类型时必须深拷贝</li>
     * </ul>
     * 
     * @return 图形的新副本
     * @throws CloneNotSupportedException 如果图形不支持克隆
     * 
     * @author Aider+SillconFlow-DeepSeek-R1
     * @since 2025-06-29
     */
    Shape clone() throws CloneNotSupportedException;
    
    /**
     * 验证坐标位置是否有效
     * 
     * <p>此方法用于检查图形坐标是否在合理范围内</p>
     * 
     * @param x x坐标(单位为像素)
     * @param y y坐标(单位为像素)
     * @throws IllegalArgumentException 当坐标为负数时抛出
     * 
     * @author Aider+DeepSeek
     * @author Aider+SillconFlow-Deepseek-R1
     * @since 2025-06-29
     */
    default void validatePosition(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("坐标不能为负数");
        }
    }

    /**
     * 移动图形的位置。
     * 
     * <p>根据给定的偏移量调整图形的坐标位置，正数表示向右/下移动，负数表示向左/上移动。</p>
     * 
     * @param dx X轴方向的移动距离（像素）
     * @param dy Y轴方向的移动距离（像素）
     * 
     * @author Aider+DeepSeek
     * @author Aider+SillconFlow-Deepseek-R1
     * @since 2025-06-29
     */
    void move(int dx, int dy);

    /**
     * 接受访问者访问此图形对象，实现访问者模式。
     * <p>允许访问者对图形对象进行操作而不修改图形本身。</p>
     * 
     * @param visitor 用于处理图形的访问者对象(非null)
     * @throws NullPointerException 如果visitor为null
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     * @author Aider+SillconFlow-DeepSeek-R1
     * @since 2025-06-29
     */
    void accept(ExportVisitor visitor);
}

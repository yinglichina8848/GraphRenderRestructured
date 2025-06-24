/**
 * 适配器类，将LegacyRenderer接口适配到新的Renderer接口。
 * 实现了适配器模式，使旧版渲染器可以在新系统中使用。
 * 
 * <p>主要功能：
 * <ul>
 *   <li>将drawCircle()适配到drawLegacyCircle()</li>
 *   <li>将drawRectangle()适配到drawLegacyRectangle()</li>
 *   <li>将drawTriangle()适配到drawLegacyTriangle()</li>
 *   <li>将drawEllipse()适配到drawLegacyEllipse()</li>
 * </ul>
 *
 * <p>典型用法：
 * <pre>{@code
 * LegacyRenderer legacy = new LegacyRendererImpl();
 * Renderer adapter = new LegacyRendererAdapter(legacy);
 * adapter.drawCircle(100, 100, 50); // 通过适配器调用旧版渲染器
 * }</pre>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @see Renderer 新渲染器接口
 * @see LegacyRenderer 旧版渲染器接口
 * @since 2025-06-24
 */
package com.example.renderer.adapter;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.legacy.LegacyRenderer;
import java.util.Objects;
import java.util.Objects;

public class LegacyRendererAdapter implements Renderer {

    private final LegacyRenderer legacyRenderer;

    /**
     * 创建LegacyRenderer适配器实例。
     * 
     * <p>构造器会保存对LegacyRenderer实例的引用，所有渲染调用都将转发给它。
     * 
     * @param legacyRenderer 要适配的旧版渲染器实例(非null)
     * @throws NullPointerException 如果legacyRenderer为null
     * @see LegacyRenderer
     */
    public LegacyRendererAdapter(LegacyRenderer legacyRenderer) {
        this.legacyRenderer = Objects.requireNonNull(legacyRenderer, "LegacyRenderer cannot be null");
    }

    /**
     * 绘制圆形，转发调用到LegacyRenderer的drawLegacyCircle()方法。
     * 
     * <p>实现细节：
     * <ul>
     *   <li>参数直接转发，不做修改</li>
     *   <li>不处理LegacyRenderer抛出的异常</li>
     * </ul>
     * 
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径(必须>0)
     * @see LegacyRenderer#drawLegacyCircle(int, int, int)
     */
    @Override
    public void drawCircle(int x, int y, int radius) {
        legacyRenderer.drawLegacyCircle(x, y, radius);
    }

    /**
     * 绘制矩形，转发调用到LegacyRenderer的drawLegacyRectangle()方法。
     * 
     * <p>实现细节：
     * <ul>
     *   <li>参数直接转发，不做修改</li>
     *   <li>不处理LegacyRenderer抛出的异常</li>
     * </ul>
     * 
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * @see LegacyRenderer#drawLegacyRectangle(int, int, int, int)
     */
    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        legacyRenderer.drawLegacyRectangle(x, y, width, height);
    }

    /**
     * 绘制三角形，转发调用到LegacyRenderer的drawLegacyTriangle()方法。
     * 
     * <p>实现细节：
     * <ul>
     *   <li>参数直接转发，不做修改</li>
     *   <li>不处理LegacyRenderer抛出的异常</li>
     * </ul>
     * 
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     * @see LegacyRenderer#drawLegacyTriangle(int, int, int, int, int, int)
     */
    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        legacyRenderer.drawLegacyTriangle(x1, y1, x2, y2, x3, y3);
    }

    /**
     * 绘制椭圆，转发调用到LegacyRenderer的drawLegacyEllipse()方法。
     * 
     * <p>实现细节：
     * <ul>
     *   <li>参数直接转发，不做修改</li>
     *   <li>不处理LegacyRenderer抛出的异常</li>
     * </ul>
     * 
     * @param x 椭圆中心x坐标
     * @param y 椭圆中心y坐标
     * @param rx 椭圆x轴半径(必须>0)
     * @param ry 椭圆y轴半径(必须>0)
     * @see LegacyRenderer#drawLegacyEllipse(int, int, int, int)
     */
    @Override
    public void drawEllipse(int x, int y, int rx, int ry) {
        legacyRenderer.drawLegacyEllipse(x, y, rx, ry);
    }
}


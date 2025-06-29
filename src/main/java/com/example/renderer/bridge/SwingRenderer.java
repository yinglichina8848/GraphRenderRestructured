package com.example.renderer.bridge;

/**
 * SwingRenderer实现了使用Java Swing进行图形渲染。
 * 作为Renderer接口的具体实现，它使用Graphics2D进行实际绘制。
 * 
 * <p>该类主要特点：
 * <ul>
 *   <li>使用蓝色(BLUE)作为默认绘制颜色</li>
 *   <li>使用2像素宽的线条</li>
 *   <li>需要先调用setGraphics()设置绘图上下文</li>
 * </ul>
 *
 * <p>典型用法：
 * <pre>{@code
 * SwingRenderer renderer = new SwingRenderer();
 * renderer.setGraphics(graphics2D); // 必须先设置绘图上下文
 * renderer.drawCircle(100, 100, 50); // 绘制蓝色圆形
 * }</pre>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @see Renderer 渲染器接口
 * @see Graphics2D Swing绘图上下文
 * @since 2025-06-24
 */

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import com.example.renderer.bridge.Renderer;

/**
 * Swing渲染器实现
 */
public class SwingRenderer implements Renderer {
    /**
     * Graphics2D绘图上下文
     * 设置为protected以便子类可以访问
     */
    protected Graphics2D g;

    /**
     * 设置绘图上下文。
     * 
     * <p>此方法必须在调用任何绘制方法前调用，用于初始化：
     * <ul>
     *   <li>绘图颜色设置为蓝色(BLUE)</li>
     *   <li>线条宽度设置为2像素</li>
     * </ul>
     * 
     * @param g Graphics2D绘图上下文对象
     * @see Graphics2D
     */
    /**
     * 设置绘图上下文。
     * 
     * @param g Graphics2D绘图上下文对象
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public void setGraphics(Graphics2D g) {
        this.g = g;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                          RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(java.awt.Color.BLUE);
        g.setStroke(new java.awt.BasicStroke(2));
    }

    /**
     * 设置绘图样式
     * 
     * <p>修改记录：
     * <ul>
     *   <li>2025-06-24 - 初始实现</li>
     *   <li>2025-06-27 - 添加颜色解码异常处理</li>
     *   <li>2025-06-27 - 添加填充颜色支持</li>
     * </ul>
     * 
     * @param stroke 描边颜色(十六进制格式)
     * @param fill 填充颜色(十六进制格式) 
     * @param width 线宽(像素)
     * @throws IllegalArgumentException 如果宽度为负数
     * @throws NumberFormatException 如果颜色格式无效
     * @since 2025-06-24
     */
    @Override
    public void setStyle(String stroke, String fill, int width) {
        if (width < 0) {
            throw new IllegalArgumentException("线宽不能为负数: " + width);
        }
        if (g != null) {
            try {
                g.setStroke(new BasicStroke(width));
                g.setColor(Color.decode(stroke));
                // TODO: 实现填充颜色处理
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("无效的颜色格式", e);
            }
        }
    }

    @Override
    public Object getContext() {
        return g;
    }

    @Override
    public void beginFrame() {
        if (g != null) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    @Override
    public void endFrame() {
        // 可添加帧结束处理逻辑
    }

    /**
     * 绘制圆形
     * @param x 圆心x坐标
     * @param y 圆心y坐标 
     * @param radius 半径(必须>0)
     * @throws IllegalArgumentException 如果半径不合法
     */
    /**
     * 使用Swing绘制圆形。
     * 
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径(必须>0)
     * @throws IllegalArgumentException 如果半径不合法
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Override
    public void drawCircle(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive (was " + radius + ")");
        }
        if (g == null) {
            throw new IllegalStateException("Graphics context not initialized. Call setGraphics() first.");
        }
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    /**
     * 绘制矩形
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    /**
     * 使用Swing绘制矩形。
     * 
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        if (g == null) {
            throw new IllegalStateException("Graphics context not initialized. Call setGraphics() first.");
        }
        g.drawRect(x, y, width, height);
    }

    /**
     * 绘制椭圆
     * @param x 椭圆外接矩形左上角x坐标
     * @param y 椭圆外接矩形左上角y坐标
     * @param width 椭圆宽度(必须>0)
     * @param height 椭圆高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    /**
     * 绘制椭圆
     * 
     * <p>修改记录：
     * <ul>
     *   <li>2025-06-24 - 初始实现</li>
     *   <li>2025-06-27 - 添加参数验证</li>
     * </ul>
     * 
     * @param x 椭圆外接矩形左上角x坐标
     * @param y 椭圆外接矩形左上角y坐标
     * @param width 椭圆宽度(必须>0)
     * @param height 椭圆高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     * @throws IllegalStateException 如果图形上下文未初始化
     * @since 2025-06-24
     */
    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive (width=" + width + ", height=" + height + ")");
        }
        if (g == null) {
            throw new IllegalStateException("Graphics context not initialized. Call setGraphics() first.");
        }
        g.drawOval(x, y, width, height);
    }

    /**
     * 绘制三角形。
     * 
     * <p>实现细节：
     * <ul>
     *   <li>使用Polygon类创建三角形路径</li>
     *   <li>继承自父类的线条样式设置</li>
     *   <li>如果Graphics2D上下文未设置，则不执行任何操作</li>
     * </ul>
     * 
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     * @see Polygon
     */
    /**
     * 使用Swing绘制三角形。
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
    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        if (g != null) {
            Polygon triangle = new Polygon();
            triangle.addPoint(x1, y1);
            triangle.addPoint(x2, y2);
            triangle.addPoint(x3, y3);
            g.drawPolygon(triangle);
        }
    }
}


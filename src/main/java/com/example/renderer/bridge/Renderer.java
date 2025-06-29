package com.example.renderer.bridge;

/**
 * 增强渲染器接口，扩展基础渲染能力。
 * 
 * <p>架构演进记录：
 * <ul>
 *   <li>v1.0 - 基础绘制方法</li>
 *   <li>v1.1 - 分离为RendererInterface和Renderer</li>
 *   <li>v1.2 - 增加样式设置和上下文访问</li>
 *   <li>v1.3 - 合并RendererInterface功能，包含基础绘制方法、样式管理、渲染上下文访问和帧控制</li>
 * </ul>
 * 
 * <p>架构角色：桥接模式中的抽象部分，将图形绘制与具体实现解耦。
 * 
 * <p>生命周期：
 * <ol>
 *   <li>通过RendererFactory创建实例</li>
 *   <li>配置样式(setStyle)</li>
 *   <li>执行绘制操作(drawXxx)</li>
 *   <li>可通过getContext获取底层实现对象</li>
 * </ol>
 * 
 * @author Aider+SillconFlow-Deepseek-R1
 * @since 2025-06-29
 * @see RendererFactory 创建实例的工厂类
 * @see RendererInterface 基础绘制接口
 */
public interface Renderer {
    /**
     * 设置渲染样式
     * @param stroke 线条颜色(支持CSS颜色格式)
     * @param fill 填充颜色(支持CSS颜色格式)
     * @param width 线宽(像素，必须>=0)
     * @throws IllegalArgumentException 如果宽度为负数
     */
    void setStyle(String stroke, String fill, int width) throws IllegalArgumentException;
    
    /**
     * 获取当前渲染上下文
     * @return 底层实现对象(Swing返回Graphics2D，SVG返回StringBuilder等)
     */
    Object getContext();
    
    /**
     * 开始新帧/页的绘制
     */
    default void beginFrame() {}
    
    /**
     * 结束当前帧/页的绘制
     */
    default void endFrame() {}
    /**
     * 绘制圆形
     * @param x 圆心x坐标
     * @param y 圆心y坐标 
     * @param radius 圆形半径(必须>0)
     * @throws IllegalArgumentException 如果半径不合法
     * @throws IllegalStateException 如果渲染器未正确初始化
     */
    void drawCircle(int x, int y, int radius) throws IllegalArgumentException, IllegalStateException;
    
    /**
     * 绘制矩形
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    void drawRectangle(int x, int y, int width, int height);
    
    /**
     * 绘制椭圆
     * @param x 椭圆外接矩形左上角x坐标
     * @param y 椭圆外接矩形左上角y坐标
     * @param width 椭圆宽度(必须>0)
     * @param height 椭圆高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    void drawEllipse(int x, int y, int width, int height);
    
    /**
     * 绘制三角形
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     */
    void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3);
}

package com.example.renderer.ui;

/**
 * DrawingPanel是SwingUI的绘图面板组件，负责显示所有图形。
 * 
 * <p>主要功能：
 * <ul>
 *   <li>维护图形列表和渲染器引用</li>
 *   <li>重写paintComponent()实现图形绘制</li>
 *   <li>默认尺寸800x600像素</li>
 * </ul>
 * 
 * @see SwingUI
 * @see Shape
 * @author liying
 * @since 2025-06-14
 */

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.bridge.SwingRenderer;
import com.example.renderer.factory.Shape;
public class DrawingPanel extends JPanel {
    /** 要渲染的图形列表 */
    private List<com.example.renderer.factory.Shape> shapes;
    
    /** 使用的渲染器实现 */
    private Renderer renderer;

    /**
     * 创建绘图面板
     * @param shapes 要显示的图形列表(非null)
     * @param renderer 使用的渲染器实现(非null)
     * @throws NullPointerException 如果shapes或renderer为null
     */
    public DrawingPanel(List<com.example.renderer.factory.Shape> shapes, Renderer renderer) {
        this.shapes = shapes;
        this.renderer = renderer;
        setPreferredSize(new Dimension(800, 600));
    }

    /**
     * 绘制组件内容
     * @param g 绘图上下文对象
     * @throws NullPointerException 如果g为null
     */
    @Override
    protected void paintComponent(Graphics g) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        super.paintComponent(g);
        System.out.println("绘制图形数量：" + shapes.size());
        if (renderer instanceof SwingRenderer swingRenderer) {
            swingRenderer.setGraphics((Graphics2D) g);
            for (Shape shape : shapes) {
                shape.render(renderer);
            }
        }
    }
}

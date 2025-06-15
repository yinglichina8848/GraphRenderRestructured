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
import com.example.renderer.bridge.Renderer;
import com.example.renderer.bridge.SwingRenderer;
import com.example.renderer.factory.Shape;
public class DrawingPanel extends JPanel {
    private List<com.example.renderer.factory.Shape> shapes;
    private Renderer renderer;

    public DrawingPanel(List<com.example.renderer.factory.Shape> shapes, Renderer renderer) {
        this.shapes = shapes;
        this.renderer = renderer;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
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

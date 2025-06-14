/**
 * DrawingPanel
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.ui;

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

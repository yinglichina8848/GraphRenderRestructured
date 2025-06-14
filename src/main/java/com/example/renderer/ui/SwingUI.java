/**
 * SwingUI
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.example.renderer.factory.Circle;
import com.example.renderer.factory.Rectangle;
import com.example.renderer.factory.Ellipse;
import com.example.renderer.factory.Triangle;
import com.example.renderer.bridge.Renderer;
import com.example.renderer.bridge.SwingRenderer;
import com.example.renderer.command.AddShapeCommand;
import com.example.renderer.command.UndoManager;


//import com.example.renderer.persistence.PersistenceManager;
import com.example.renderer.singleton.PersistenceManager;



public class SwingUI extends JFrame {
    private final List<com.example.renderer.factory.Shape> shapes = new ArrayList<>();
    private Renderer renderer;
    private UndoManager undoManager = new UndoManager();
    private DrawingPanel drawingPanel;  // 新增绘图面板变量


    public SwingUI() {
        setTitle("图形渲染系统 - Swing 可视化");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        renderer = new SwingRenderer();

        drawingPanel = new DrawingPanel(shapes, renderer);
        add(drawingPanel, BorderLayout.CENTER);  // 添加绘图面板到中心区域

        JButton btnCircle = new JButton("添加圆形");
        JButton btnRect = new JButton("添加矩形");
        JButton btnEllipse = new JButton("添加椭圆");
        JButton btnTriangle = new JButton("添加三角形");
        JButton btnUndo = new JButton("撤销");
        JButton btnRedo = new JButton("重做");

        JPanel panel = new JPanel();
        panel.add(btnCircle);
        panel.add(btnRect);
        panel.add(btnEllipse);
        panel.add(btnTriangle);
        panel.add(btnUndo);
        panel.add(btnRedo);

        btnCircle.addActionListener(e -> {
            Circle c = new Circle(100 + shapes.size() * 10, 100, 30 + shapes.size() * 5);
            AddShapeCommand cmd = new AddShapeCommand(shapes, c);
            undoManager.executeCommand(cmd);
            repaint();
        });

        btnRect.addActionListener(e -> {
            Rectangle r = new Rectangle(200, 150 + shapes.size() * 10, 60, 40);
            AddShapeCommand cmd = new AddShapeCommand(shapes, r);
            undoManager.executeCommand(cmd);
            repaint();
        });

        btnEllipse.addActionListener(e -> {
            Ellipse e1 = new Ellipse(300, 200, 50, 30);
            AddShapeCommand cmd = new AddShapeCommand(shapes, e1);
            undoManager.executeCommand(cmd);
            repaint();
        });

        btnTriangle.addActionListener(e -> {
            Triangle t = new Triangle(400, 300, 450, 350, 420, 390);
            AddShapeCommand cmd = new AddShapeCommand(shapes, t);
            undoManager.executeCommand(cmd);
            repaint();
        });

        btnUndo.addActionListener(e -> {
            undoManager.undo();
            repaint();
        });

        btnRedo.addActionListener(e -> {
            undoManager.redo();
            repaint();
        });

        JButton btnSave = new JButton("保存图形");
        btnSave.addActionListener(e -> saveShapes());

        JButton btnLoad = new JButton("载入图形");
        btnLoad.addActionListener(e -> loadShapes());

        panel.add(btnSave);
        panel.add(btnLoad);

        add(panel, BorderLayout.SOUTH);

        pack();  // 让窗口根据内容尺寸调整
        setLocationRelativeTo(null);  // 窗口居中
        setVisible(true);


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (renderer instanceof SwingRenderer swingRenderer) {
            swingRenderer.setGraphics((Graphics2D) g);
            for (com.example.renderer.factory.Shape shape : shapes) {
                shape.render(renderer);
            }
        }
    }


    private void saveShapes() {
        JFileChooser chooser = new JFileChooser();
        int ret = chooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                PersistenceManager.getInstance().saveShapesToFile(shapes, file.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "保存成功");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "保存失败: " + ex.getMessage());
            }
        }
    }


    private void loadShapes() {
        JFileChooser chooser = new JFileChooser();
        int ret = chooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                List<com.example.renderer.factory.Shape> loadedShapes = PersistenceManager.getInstance().loadShapesFromFile(file.getAbsolutePath());
                shapes.clear();
                shapes.addAll(loadedShapes);
                drawingPanel.repaint();  // 这里刷新绘图面板

                JOptionPane.showMessageDialog(this, "载入成功");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "载入失败: " + ex.getMessage());
            }
        }
    }

}


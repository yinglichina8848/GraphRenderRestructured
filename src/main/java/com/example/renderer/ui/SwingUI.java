package com.example.renderer.ui;

/**
 * SwingUI是图形渲染系统的主界面，使用Swing实现。
 * 
 * <p>主要功能组件：
 * <ul>
 *   <li>绘图面板(DrawingPanel) - 显示图形</li>
 *   <li>控制按钮 - 添加圆形、矩形、椭圆和三角形</li>
 *   <li>撤销/重做功能 - 通过UndoManager实现命令历史管理</li>
 *   <li>文件操作 - 通过PersistenceManager实现图形的保存和加载</li>
 * </ul>
 * 
 * <p>设计模式应用：
 * <ul>
 *   <li>命令模式 - 通过Command接口实现操作封装</li>
 *   <li>桥接模式 - 通过Renderer接口分离渲染逻辑</li>
 *   <li>单例模式 - 使用PersistenceManager管理持久化</li>
 *   <li>观察者模式 - 图形变化时自动重绘</li>
 * </ul>
 *
 * <p>典型使用场景：
 * <pre>{@code
 * SwingUI ui = new SwingUI(); // 创建并显示界面
 * }</pre>
 * 
 * @see DrawingPanel 绘图面板实现
 * @see UndoManager 撤销/重做管理
 * @see PersistenceManager 持久化管理器
 * @author liying
 * @since 1.0
 * @version 1.0.0
 */

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



/**
 * SwingUI是图形渲染系统的主界面，使用Swing实现。
 * 
 * <p>主要功能组件：
 * <ul>
 *   <li>绘图面板(DrawingPanel) - 显示图形</li>
 *   <li>控制按钮 - 添加圆形、矩形、椭圆和三角形</li>
 *   <li>撤销/重做功能 - 通过UndoManager实现命令历史管理</li>
 *   <li>文件操作 - 通过PersistenceManager实现图形的保存和加载</li>
 * </ul>
 * 
 * @see DrawingPanel 绘图面板实现
 * @see UndoManager 撤销/重做管理
 * @see PersistenceManager 持久化管理器
 * @author liying
 * @since 1.0
 */
public class SwingUI extends JFrame {
    /** 图形列表 */
    private final List<com.example.renderer.factory.Shape> shapes = new ArrayList<>();
    
    /** 渲染器实现 */
    private Renderer renderer;
    
    /** 撤销管理器 */
    private UndoManager undoManager = new UndoManager();
    
    /** 绘图面板组件 */
    private DrawingPanel drawingPanel;


    /**
     * 创建并初始化Swing图形用户界面。
     * 
     * <p>初始化内容包括：
     * <ul>
     *   <li>设置窗口标题和大小</li>
     *   <li>创建绘图面板</li>
     *   <li>添加各种图形按钮</li>
     *   <li>设置按钮事件监听器</li>
     *   <li>添加撤销/重做功能</li>
     *   <li>添加文件保存/加载功能</li>
     * </ul>
     */
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

        // 添加圆形按钮事件处理
        btnCircle.addActionListener(e -> {
            // 创建圆形实例，位置和半径根据已有图形数量动态调整
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

    // 移除paint()方法重写，完全使用DrawingPanel进行绘制


    /**
     * 保存当前图形列表到文件。
     * 使用JFileChooser让用户选择保存位置，然后通过PersistenceManager
     * 将图形列表序列化为JSON格式保存。
     * 
     * <p>处理流程：
     * 1. 显示文件选择对话框
     * 2. 用户选择保存位置
     * 3. 调用PersistenceManager保存数据
     * 4. 显示操作结果提示
     * 
     * @throws IOException 如果文件写入失败
     * @see PersistenceManager#saveShapesToFile(List, String)
     */
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


    /**
     * 从文件加载图形列表。
     * 使用JFileChooser让用户选择要加载的文件，然后通过PersistenceManager
     * 从JSON格式反序列化为图形对象列表。
     * 
     * <p>处理流程：
     * 1. 显示文件选择对话框
     * 2. 用户选择要加载的文件
     * 3. 调用PersistenceManager加载数据
     * 4. 清空当前图形列表并添加加载的图形
     * 5. 刷新绘图面板显示新图形
     * 6. 显示操作结果提示
     * 
     * @throws IOException 如果文件读取失败
     * @throws JsonParseException 如果JSON解析失败
     * @see PersistenceManager#loadShapesFromFile(String) 
     */
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


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

import com.example.renderer.bridge.Renderer;
import com.example.renderer.bridge.RendererFactory;
import com.example.renderer.config.GlobalConfig;
import com.example.renderer.exception.RendererCreationException;
import com.example.renderer.factory.*;
import com.example.renderer.command.*;
import com.example.renderer.singleton.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(SwingUI.class);
    /** 图形列表 */
    final List<com.example.renderer.factory.Shape> shapes = new ArrayList<>();
    
    /** 渲染器实现 */
    Renderer renderer;
    
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


        // 通过配置获取渲染器
        String mode = GlobalConfig.getInstance().getRenderMode();
        try {
            renderer = RendererFactory.create(mode);
        } catch (RendererCreationException e) {
            LOGGER.error("Renderer initialization failed", e);
            JOptionPane.showMessageDialog(this,
                String.format("无法初始化渲染器 %s: %s\n将使用默认Swing渲染器",
                    mode, e.getMessage()),
                "渲染器错误", JOptionPane.ERROR_MESSAGE);
            renderer = createFallbackRenderer();
        }

        drawingPanel = new DrawingPanel(shapes, renderer);
        add(drawingPanel, BorderLayout.CENTER);  // 添加绘图面板到中心区域

        JButton btnCircle = new JButton("添加圆形");
        btnCircle.setName("btnCircle");
        JButton btnRect = new JButton("添加矩形");
        btnRect.setName("btnRect");
        JButton btnEllipse = new JButton("添加椭圆");
        btnEllipse.setName("btnEllipse");
        JButton btnTriangle = new JButton("添加三角形");
        btnTriangle.setName("btnTriangle");
        JButton btnUndo = new JButton("撤销");
        btnUndo.setName("btnUndo");
        JButton btnRedo = new JButton("重做");
        btnRedo.setName("btnRedo");

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
        btnSave.setName("btnSave");
        btnSave.addActionListener(e -> saveShapes());

        JButton btnLoad = new JButton("载入图形");
        btnLoad.setName("btnLoad");
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
     * @see PersistenceManager#saveShapesToFile(List, String)
     */
    private void saveShapes() {
        File file = selectSaveFile();
        if (file != null) {
            try {
                PersistenceManager.getInstance().saveShapesToFile(shapes, file.getAbsolutePath());
                showMessage("保存成功");
            } catch (Exception ex) {
                showMessage("保存失败: " + ex.getMessage());
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
     * @see PersistenceManager#loadShapesFromFile(String) 
     */
    /**
     * 创建回退渲染器（包级访问权限，供单元测试使用）
     * @return 渲染器实例
     */
    Renderer createFallbackRenderer() {
        try {
            return new SwingRenderer();
        } catch (Exception e) {
            LOGGER.error("Fallback renderer creation failed", e);
            throw new IllegalStateException("无法创建回退渲染器", e);
        }
    }


    protected void loadShapes() {
        File file = selectOpenFile();
        if (file != null) {
            try {
                List<com.example.renderer.factory.Shape> loadedShapes = PersistenceManager.getInstance().loadShapesFromFile(file.getAbsolutePath());
                shapes.clear();
                shapes.addAll(loadedShapes);
                drawingPanel.repaint();
                showMessage("载入成功");
            } catch (Exception ex) {
                showMessage("载入失败: " + ex.getMessage());
            }
        }
    }

    protected File selectSaveFile() {
        JFileChooser chooser = new JFileChooser();
        int ret = chooser.showSaveDialog(this);
        return (ret == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
    }

    protected File selectOpenFile() {
        JFileChooser chooser = new JFileChooser();
        int ret = chooser.showOpenDialog(this);
        return (ret == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
    }

    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}


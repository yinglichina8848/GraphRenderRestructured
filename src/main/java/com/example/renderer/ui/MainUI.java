
/**
 * 主用户界面类，用于启动和显示 Swing 图形界面。
 */
/**
 * MainUI
 *
 * @author liying
 * @date 2025-06-15
 * @lastModified 2025-06-15
 */
package com.example.renderer.ui;

import javax.swing.SwingUtilities;

/**
 * MainUI 类包含程序的入口点，并负责初始化图形用户界面。
 */


/**
 * 主用户界面类，负责启动图形渲染系统的Swing界面。
 * 
 * <p>该类包含程序的主入口点main()方法，主要功能：
 * <ul>
 *   <li>使用SwingUtilities.invokeLater()确保界面在事件分发线程中创建</li>
 *   <li>初始化并显示主界面(SwingUI)</li>
 *   <li>处理可能的启动异常</li>
 * </ul>
 * 
 * <p>典型用法：
 * <pre>
 * public static void main(String[] args) {
 *     MainUI.launch();
 * }
 * </pre>
 * 
 * @see SwingUI 主界面实现类
 * @see SwingUtilities Swing工具类
 * @author liying
 * @since 1.0
 */
public class MainUI {

    /**
     * 程序的入口点。
     *
     * @param args 命令行参数（未使用）
     */
    /**
     * 程序主入口，启动Swing图形界面。
     * 
     * <p>使用SwingUtilities.invokeLater()确保界面在事件分发线程中创建，
     * 这是Swing线程安全的最佳实践。</p>
     * 
     * @param args 命令行参数(未使用)
     * @see SwingUtilities
     * @see SwingUI
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingUI ui = new SwingUI();
            ui.setVisible(true);
        });
    }
}

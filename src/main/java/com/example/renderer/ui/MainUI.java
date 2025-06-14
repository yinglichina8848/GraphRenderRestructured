
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


public class MainUI {

    /**
     * 程序的入口点。
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingUI ui = new SwingUI();
            ui.setVisible(true);
        });
    }
}

package com.example.renderer.bridge;

/**
 * 测试用渲染器，仅记录调用不实际渲染
 */
public class TestRenderer implements Renderer {
    @Override
    public void drawCircle(int x, int y, int radius) {
        System.out.printf("TestRenderer: drawCircle(%d,%d,%d)\n", x, y, radius);
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        System.out.printf("TestRenderer: drawRectangle(%d,%d,%d,%d)\n", 
            x, y, width, height);
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        System.out.printf("TestRenderer: drawEllipse(%d,%d,%d,%d)\n",
            x, y, width, height);
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("TestRenderer: drawTriangle(%d,%d,%d,%d,%d,%d)\n",
            x1, y1, x2, y2, x3, y3);
    }
}

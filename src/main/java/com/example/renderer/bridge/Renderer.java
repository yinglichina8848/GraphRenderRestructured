package com.example.renderer.bridge;


public interface Renderer {
    void drawCircle(int x, int y, int radius);
    void drawRectangle(int x, int y, int width, int height);
    void drawEllipse(int x, int y, int width, int height);
    void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3);  // 新增
}

class SVGRenderer implements Renderer {
    public void drawCircle(int x, int y, int radius) {
        System.out.printf("<circle cx='%d' cy='%d' r='%d' />\n", x, y, radius);
    }
    public void drawRectangle(int x, int y, int width, int height) {
        System.out.printf("<rect x='%d' y='%d' width='%d' height='%d' />\n", x, y, width, height);
    }

    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("<polygon points='%d,%d %d,%d %d,%d' />\n", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        System.out.printf("<ellipse cx='%d' cy='%d' rx='%d' ry='%d' />\n", x, y, width / 2, height / 2);
    }

}
package com.example.renderer.bridge;

public interface RendererInterface {
    void drawCircle(int x, int y, int radius);
    void drawRectangle(int x, int y, int width, int height);
    void drawEllipse(int x, int y, int width, int height); 
    void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3);
}

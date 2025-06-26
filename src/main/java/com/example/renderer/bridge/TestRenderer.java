package com.example.renderer.bridge;

/**
 * 测试用渲染器，仅记录调用不实际渲染
 */
public class TestRenderer implements Renderer {
    private Object testContext = new Object();

    @Override
    public void setStyle(String stroke, String fill, int width) {
        System.out.printf("TestRenderer: 设置样式 - 描边:%s 填充:%s 线宽:%d\n", 
                         stroke, fill, width);
    }

    @Override
    public Object getContext() {
        return testContext;
    }

    @Override
    public void beginFrame() {
        System.out.println("TestRenderer: 开始新帧");
    }

    @Override
    public void endFrame() {
        System.out.println("TestRenderer: 结束帧");
    }
    @Override
    public void drawCircle(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("半径必须为正数 (当前值: " + radius + ")");
        }
        System.out.printf("[DEBUG] TestRenderer.drawCircle - 位置: (%d,%d), 半径: %d, 面积: %.2f\n", 
            x, y, radius, Math.PI * radius * radius);
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

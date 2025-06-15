// 5. RemoteRendererProxy.java
package com.example.renderer.proxy;

/**
 * RemoteRendererProxy是远程渲染器的代理类，实现了代理模式。
 * 在调用实际渲染方法前后添加日志记录等控制逻辑。
 * 
 * @see Renderer
 * @author liying
 * @since 2025-06-14
 */

import com.example.renderer.bridge.Renderer;

public class RemoteRendererProxy implements Renderer {
    private final Renderer realRenderer;

    public RemoteRendererProxy(Renderer realRenderer) {
        this.realRenderer = realRenderer;
    }

    public void drawCircle(int x, int y, int radius) {
        System.out.println("[Proxy] Sending drawCircle to remote...");
        realRenderer.drawCircle(x, y, radius);
    }

    public void drawRectangle(int x, int y, int width, int height) {
        System.out.println("[Proxy] Sending drawRectangle to remote...");
        realRenderer.drawRectangle(x, y, width, height);
    }


    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("Remote call: drawTriangle(%d, %d, %d, %d, %d, %d)\n", x1, y1, x2, y2, x3, y3);
        realRenderer.drawTriangle(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        System.out.printf("Remote call: drawEllipse(%d, %d, %d, %d)\n", x, y, width, height);
        realRenderer.drawEllipse(x, y, width, height);
    }
}

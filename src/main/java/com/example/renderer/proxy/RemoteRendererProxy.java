// 5. RemoteRendererProxy.java
package com.example.renderer.proxy;

/**
 * 远程渲染器代理类，实现了代理模式。
 * 
 * <p>主要功能：
 * <ul>
 *   <li>在调用实际渲染方法前后添加日志记录</li>
 *   <li>控制对真实渲染器的访问</li>
 *   <li>可以添加额外的网络通信逻辑</li>
 * </ul>
 * 
 * @see Renderer 渲染器接口
 * @see Proxy 代理模式
 * @author liying
 * @since 1.0
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

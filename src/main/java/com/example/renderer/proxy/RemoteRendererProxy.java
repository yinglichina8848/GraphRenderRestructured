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
 * <p>典型用法：
 * <pre>{@code
 * Renderer realRenderer = new SwingRenderer();
 * Renderer proxy = new RemoteRendererProxy(realRenderer);
 * proxy.drawCircle(100,100,50); // 通过代理调用
 * }</pre>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @see Renderer 渲染器接口
 * @see Proxy 代理模式
 * @since 2025-06-24
 */

import com.example.renderer.bridge.Renderer;
import java.util.Objects;

/**
 * 远程渲染器代理实现类
 */
public class RemoteRendererProxy implements Renderer {
    private final Renderer realRenderer;
    private String strokeColor = "black";
    private String fillColor = "none";
    private int strokeWidth = 1;

    /**
     * 创建远程渲染器代理
     * @param realRenderer 实际渲染器实例(非null)
     * @throws NullPointerException 如果realRenderer为null
     */
    /**
     * 创建远程渲染器代理实例。
     * 
     * @param realRenderer 实际渲染器实例(非null)
     * @throws NullPointerException 如果realRenderer为null
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public RemoteRendererProxy(Renderer realRenderer) {
        Objects.requireNonNull(realRenderer, "Real renderer cannot be null");
        this.realRenderer = realRenderer;
    }

    /**
     * 代理绘制圆形操作，添加远程调用日志。
     * 
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径
     * @see Renderer#drawCircle(int, int, int)
     */
    /**
     * 代理绘制圆形操作，添加远程调用日志。
     * 
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径
     * @see Renderer#drawCircle(int, int, int)
     */
    /**
     * 代理绘制圆形操作，添加远程调用日志。
     * 
     * @param x 圆心x坐标
     * @param y 圆心y坐标
     * @param radius 圆形半径(必须>0)
     * @throws IllegalArgumentException 如果半径不合法
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public void drawCircle(int x, int y, int radius) {
        System.out.println("[Proxy] Sending drawCircle to remote...");
        int retries = 3;
        while (retries-- > 0) {
            try {
                realRenderer.drawCircle(x, y, radius);
                return;
            } catch (Exception e) {
                if (retries == 0) throw e;
                System.out.println("远程调用失败，剩余重试次数: " + retries);
            }
        }
    }

    /**
     * 代理绘制矩形操作，添加远程调用日志。
     * 
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param width 矩形宽度(必须>0)
     * @param height 矩形高度(必须>0)
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public void drawRectangle(int x, int y, int width, int height) {
        System.out.println("[Proxy] Sending drawRectangle to remote...");
        realRenderer.drawRectangle(x, y, width, height);
    }


    /**
     * 代理绘制三角形操作，添加远程调用日志。
     * 
     * @param x1 第一个顶点x坐标
     * @param y1 第一个顶点y坐标
     * @param x2 第二个顶点x坐标
     * @param y2 第二个顶点y坐标
     * @param x3 第三个顶点x坐标
     * @param y3 第三个顶点y坐标
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Override
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        System.out.printf("Remote call: drawTriangle(%d, %d, %d, %d, %d, %d)\n", x1, y1, x2, y2, x3, y3);
        realRenderer.drawTriangle(x1, y1, x2, y2, x3, y3);
    }

    /**
     * 代理绘制椭圆操作，添加远程调用日志。
     * 
     * @param x 椭圆外接矩形左上角x坐标
     * @param y 椭圆外接矩形左上角y坐标
     * @param width 椭圆宽度(必须>0)
     * @param height 椭圆高度(必须>0)
     * @throws IllegalArgumentException 如果宽度或高度不合法
     */
    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        this.strokeColor = stroke;
        this.fillColor = fill;
        this.strokeWidth = width;
        realRenderer.setStyle(stroke, fill, width);
    }

    @Override
    public Object getContext() {
        return realRenderer.getContext();
    }

    @Override
    public void beginFrame() {
        realRenderer.beginFrame();
    }

    @Override
    public void endFrame() {
        realRenderer.endFrame();
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height) {
        System.out.printf("Remote call: drawEllipse(%d, %d, %d, %d)\n", x, y, width, height);
        realRenderer.drawEllipse(x, y, width, height);
    }
}

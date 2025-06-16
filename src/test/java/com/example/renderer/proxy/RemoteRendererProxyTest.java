package com.example.renderer.proxy;

import com.example.renderer.bridge.Renderer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * RemoteRendererProxy的单元测试类。
 * 
 * <p>测试代理模式是否正确转发调用到真实渲染器，并验证：
 * <ul>
 *   <li>方法调用被正确转发</li>
 *   <li>参数传递正确</li>
 *   <li>边界值处理</li>
 * </ul>
 * 
 * @see RemoteRendererProxy
 * @author liying
 * @since 1.0
 */
public class RemoteRendererProxyTest {

    /**
     * 测试代理模式是否正确转发所有渲染方法调用到真实渲染器。
     * 
     * <p>验证点：
     * <ul>
     *   <li>drawCircle()调用被正确转发</li>
     *   <li>drawRectangle()调用被正确转发</li>
     *   <li>drawTriangle()调用被正确转发</li>
     *   <li>drawEllipse()调用被正确转发</li>
     * </ul>
     * 
     * <p>测试方法：
     * <ol>
     *   <li>创建Mock渲染器和代理实例</li>
     *   <li>调用代理的各种绘制方法</li>
     *   <li>验证Mock渲染器是否收到正确调用</li>
     * </ol>
     */
    @Test
    public void testProxyCallsRealRenderer() {
        Renderer mockRenderer = mock(Renderer.class);
        RemoteRendererProxy proxy = new RemoteRendererProxy(mockRenderer);
        
        proxy.drawCircle(10, 20, 30);
        verify(mockRenderer).drawCircle(10, 20, 30);
        
        proxy.drawRectangle(10, 20, 30, 40);
        verify(mockRenderer).drawRectangle(10, 20, 30, 40);
        
        proxy.drawTriangle(1, 2, 3, 4, 5, 6);
        verify(mockRenderer).drawTriangle(1, 2, 3, 4, 5, 6);
        
        proxy.drawEllipse(10, 20, 30, 40);
        verify(mockRenderer).drawEllipse(10, 20, 30, 40);
    }

    // 边界值测试
    @Test
    public void testProxy_ExtremeCoordinates() {
        Renderer mockRenderer = mock(Renderer.class);
        RemoteRendererProxy proxy = new RemoteRendererProxy(mockRenderer);
        
        proxy.drawCircle(Integer.MAX_VALUE, Integer.MIN_VALUE, 1);
        verify(mockRenderer).drawCircle(Integer.MAX_VALUE, Integer.MIN_VALUE, 1);
        
        proxy.drawRectangle(Integer.MAX_VALUE, Integer.MIN_VALUE, 1, 1);
        verify(mockRenderer).drawRectangle(Integer.MAX_VALUE, Integer.MIN_VALUE, 1, 1);
    }

    @Test
    public void testConstructor_NullRenderer() {
        assertThrows(NullPointerException.class, () -> new RemoteRendererProxy(null));
    }
}

package com.example.renderer.proxy;

import com.example.renderer.bridge.Renderer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RemoteRendererProxyTest {

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

/**
 * LegacyRendererAdapterTest
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.adapter;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.adapter.LegacyRendererAdapter;
import com.example.renderer.legacy.LegacyRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * LegacyRendererAdapter 的单元测试。
 */
public class LegacyRendererAdapterTest {

    private LegacyRenderer mockLegacyRenderer;
    private Renderer renderer;

    @BeforeEach
    public void setUp() {
        mockLegacyRenderer = Mockito.mock(LegacyRenderer.class);
        renderer = new LegacyRendererAdapter(mockLegacyRenderer);
    }

    @Test
    public void testDrawCircle_ForwardsToLegacyRenderer() {
        int x = 10, y = 20, radius = 30;
        renderer.drawCircle(x, y, radius);
        verify(mockLegacyRenderer).drawLegacyCircle(x, y, radius);
    }

    @Test
    public void testDrawRectangle_ForwardsToLegacyRenderer() {
        int x = 10, y = 20, width = 40, height = 50;
        renderer.drawRectangle(x, y, width, height);
        verify(mockLegacyRenderer).drawLegacyRectangle(x, y, width, height);
    }

    @Test
    public void testDrawTriangle_ForwardsToLegacyRenderer() {
        int x1 = 10, y1 = 20, x2 = 30, y2 = 40, x3 = 50, y3 = 60;
        renderer.drawTriangle(x1, y1, x2, y2, x3, y3);
        verify(mockLegacyRenderer).drawLegacyTriangle(x1, y1, x2, y2, x3, y3);
    }

    @Test
    public void testDrawEllipse_ForwardsToLegacyRenderer() {
        int x = 10, y = 20, rx = 30, ry = 40;
        renderer.drawEllipse(x, y, rx, ry);
        verify(mockLegacyRenderer).drawLegacyEllipse(x, y, rx, ry);
    }
}

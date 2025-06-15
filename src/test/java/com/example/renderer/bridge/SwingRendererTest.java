/**
 * SwingRendererTest
 *
 * @author liying
 * @date 2025-06-15
 * @lastModified 2025-06-15
 */
package com.example.renderer.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.mockito.Mockito.*;

/**
 * SwingRenderer 的单元测试。
 */
public class SwingRendererTest {

    private SwingRenderer renderer;
    private Graphics2D mockGraphics;

    @BeforeEach
    public void setUp() {
        renderer = new SwingRenderer();
        mockGraphics = mock(Graphics2D.class);
        renderer.setGraphics(mockGraphics);
    }

    @Test
    public void testDrawCircle_DelegatesToGraphicsDrawOval() {
        int x = 10, y = 20, radius = 30;
        renderer.drawCircle(x, y, radius);

        verify(mockGraphics).drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Test
    public void testDrawRectangle_DelegatesToGraphicsDrawRect() {
        int x = 10, y = 20, width = 40, height = 50;
        renderer.drawRectangle(x, y, width, height);

        verify(mockGraphics).drawRect(x, y, width, height);
    }

    @Test
    public void testDrawEllipse_DelegatesToGraphicsDrawOval() {
        int x = 10, y = 20, width = 40, height = 50;
        renderer.drawEllipse(x, y, width, height);

        verify(mockGraphics).drawOval(x, y, width, height);
    }

    @Test
    public void testDrawTriangle_DelegatesToGraphicsDrawPolygon() {
        int x1 = 10, y1 = 20, x2 = 30, y2 = 40, x3 = 50, y3 = 60;
        renderer.drawTriangle(x1, y1, x2, y2, x3, y3);

        verify(mockGraphics).drawPolygon(argThat(polygon ->
                polygon.npoints == 3 &&
                polygon.xpoints[0] == x1 && polygon.ypoints[0] == y1 &&
                polygon.xpoints[1] == x2 && polygon.ypoints[1] == y2 &&
                polygon.xpoints[2] == x3 && polygon.ypoints[2] == y3));
    }
}

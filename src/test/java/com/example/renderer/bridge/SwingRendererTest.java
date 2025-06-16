/**
 * SVGRenderer的单元测试类。
 * 
 * <p>验证SVG渲染器的以下功能：
 * <ul>
 *   <li>正确生成SVG格式字符串</li>
 *   <li>各种图形的SVG输出格式</li>
 *   <li>边界值处理</li>
 *   <li>输出流重定向</li>
 * </ul>
 * 
 * <p>注意：部分测试需要GUI环境，使用@Disabled注解标记。</p>
 * 
 * @see SVGRenderer 被测试类
 * @author liying
 * @since 1.0
 */
package com.example.renderer.bridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.awt.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * SwingRenderer的单元测试类。
 * 
 * <p>验证Swing渲染器的以下功能：
 * <ul>
 *   <li>正确委托给Graphics2D对象</li>
 *   <li>各种图形的渲染调用</li>
 *   <li>极端坐标处理</li>
 *   <li>退化图形情况</li>
 * </ul>
 * 
 * <p>使用Mockito模拟Graphics2D对象进行验证。</p>
 * 
 * @see SwingRenderer 被测试类
 * @author liying
 * @since 1.0
 */
@Tag("gui")
@EnabledOnOs({OS.WINDOWS, OS.MAC, OS.LINUX})
@EnabledIfSystemProperty(named = "java.awt.headless", matches = "false")
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

    // 边界值测试
    @Test
    public void testDrawCircle_InvalidRadius() {
        assertThrows(IllegalArgumentException.class, () -> 
            renderer.drawCircle(10, 10, -1));
    }

    @Test
    public void testDrawCircle_MaxRadius() {
        int x = Integer.MAX_VALUE/2, y = Integer.MAX_VALUE/2;
        int radius = Integer.MAX_VALUE/2 - 1;
        renderer.drawCircle(x, y, radius);
        verify(mockGraphics).drawOval(1, 1, Integer.MAX_VALUE-2, Integer.MAX_VALUE-2);
    }

    @Test
    public void testDrawRectangle_ExtremeCoordinates() {
        renderer.drawRectangle(Integer.MAX_VALUE, Integer.MIN_VALUE, 1, 1);
        verify(mockGraphics).drawRect(Integer.MAX_VALUE, Integer.MIN_VALUE, 1, 1);
    }

    @Test
    public void testDrawTriangle_DegenerateTriangle() {
        renderer.drawTriangle(0, 0, 0, 0, 0, 0);
        verify(mockGraphics).drawPolygon(any());
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

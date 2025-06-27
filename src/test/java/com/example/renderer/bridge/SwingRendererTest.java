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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.awt.*;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.junit.jupiter.api.Assertions.*;

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
    public void testDrawCircle() {
        renderer.drawCircle(100, 100, 50);
        verify(mockGraphics).drawOval(50, 50, 100, 100);
    }

    @Test
    public void testDrawRectangle() {
        renderer.drawRectangle(50, 50, 100, 80);
        verify(mockGraphics).drawRect(50, 50, 100, 80);
    }

    @Test
    public void testInvalidRadius() {
        assertThrows(IllegalArgumentException.class,
            () -> renderer.drawCircle(100, 100, -1));
    }

    @Test
    public void testUninitializedGraphics() {
        SwingRenderer uninitRenderer = new SwingRenderer();
        assertThrows(IllegalStateException.class,
            () -> uninitRenderer.drawCircle(100, 100, 50));
    }

    @Test
    public void testStyleSetting() {
        // Reset mock to ignore initial setup calls
        reset(mockGraphics);
        
        renderer.setStyle("#FF0000", "#00FF00", 2);
        verify(mockGraphics).setColor(Color.RED);
        verify(mockGraphics).setStroke(argThat(stroke -> 
            stroke instanceof BasicStroke && ((BasicStroke)stroke).getLineWidth() == 2));
    }

    /**
     * 测试无效颜色格式
     * @since 2025-06-27
     */
    @Test
    public void testInvalidColorFormat() {
        assertThrows(IllegalArgumentException.class,
            () -> renderer.setStyle("invalidColor", "#00FF00", 1));
    }

    /**
     * 测试负线宽
     * @since 2025-06-27
     */
    @Test
    public void testNegativeStrokeWidth() {
        assertThrows(IllegalArgumentException.class,
            () -> renderer.setStyle("#FF0000", "#00FF00", -1));
    }

    /**
     * 测试三角形绘制
     * @since 2025-06-27
     */
    /**
     * 测试三角形绘制
     * @since 2025-06-27
     */
    @Test
    public void testDrawTriangle() {
        renderer.drawTriangle(10, 10, 20, 30, 30, 10);
        verify(mockGraphics).drawPolygon(any(Polygon.class));
    }

    /**
     * 测试椭圆绘制
     * @since 2025-06-27
     */
    @Test
    public void testDrawEllipse() {
        renderer.drawEllipse(10, 20, 30, 40);
        verify(mockGraphics).drawOval(10, 20, 30, 40);
    }

    /**
     * 测试未初始化图形上下文时的椭圆绘制
     * @since 2025-06-27
     */
    @Test
    public void testDrawEllipseWithoutGraphics() {
        SwingRenderer uninitRenderer = new SwingRenderer();
        assertThrows(IllegalStateException.class,
            () -> uninitRenderer.drawEllipse(10, 20, 30, 40));
    }

    /**
     * 测试无效椭圆参数
     * @since 2025-06-27
     */
    @Test
    public void testInvalidEllipseParameters() {
        assertThrows(IllegalArgumentException.class,
            () -> renderer.drawEllipse(10, 20, -30, 40));
        assertThrows(IllegalArgumentException.class,
            () -> renderer.drawEllipse(10, 20, 30, -40));
    }

    /**
     * 测试三角形顶点验证
     * @since 2025-06-27
     */
    @Test
    public void testTriangleVertices() {
        renderer.drawTriangle(10, 10, 20, 30, 30, 10);
        verify(mockGraphics).drawPolygon(argThat(polygon -> 
            polygon.npoints == 3 &&
            polygon.xpoints[0] == 10 &&
            polygon.ypoints[1] == 30));
    }

    /**
     * 测试图形上下文获取
     * @since 2025-06-27
     */
    @Test
    public void testGetContext() {
        assertSame(mockGraphics, renderer.getContext());
    }

    /**
     * 测试帧控制方法
     * @since 2025-06-27
     */
    @Test
    public void testFrameControl() {
        renderer.beginFrame();
        verify(mockGraphics).setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        renderer.endFrame();
        // 验证无异常抛出
    }
}

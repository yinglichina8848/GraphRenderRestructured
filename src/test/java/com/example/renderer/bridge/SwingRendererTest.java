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
        renderer.setStyle("#FF0000", "#00FF00", 2);
        verify(mockGraphics).setColor(Color.RED);
        verify(mockGraphics).setStroke(argThat(stroke -> 
            stroke instanceof BasicStroke && ((BasicStroke)stroke).getLineWidth() == 2));
    }
}

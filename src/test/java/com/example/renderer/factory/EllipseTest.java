package com.example.renderer.factory;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Ellipse类的单元测试。
 * 
 * <p>验证椭圆图形的以下行为：
 * <ul>
 *   <li>正确渲染椭圆</li>
 *   <li>移动操作的正确性</li>
 *   <li>边界尺寸处理</li>
 *   <li>访问者模式支持</li>
 *   <li>极端坐标情况</li>
 * </ul>
 * 
 * @see Ellipse 被测试类
 * @author liying
 * @since 1.0
 */
public class EllipseTest {

    @Test
    public void testRender() {
        Ellipse ellipse = new Ellipse(10, 20, 30, 40);
        Renderer mockRenderer = mock(Renderer.class);
        
        ellipse.render(mockRenderer);
        
        verify(mockRenderer).drawEllipse(10, 20, 30, 40);
    }

    @Test
    public void testMove() {
        Ellipse ellipse = new Ellipse(10, 20, 30, 40);
        ellipse.move(5, -5);
        assertEquals(15, ellipse.getX());
        assertEquals(15, ellipse.getY());
    }

    // 边界值测试
    @Test
    public void testCreate_ExtremeDimensions() {
        Ellipse ellipse = new Ellipse(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, ellipse.getWidth());
        assertEquals(Integer.MAX_VALUE, ellipse.getHeight());
    }

    @Test
    public void testMove_ToBoundary() {
        Ellipse ellipse = new Ellipse(Integer.MAX_VALUE - 10, Integer.MIN_VALUE + 10, 20, 20);
        ellipse.move(10, -10);
        assertEquals(Integer.MAX_VALUE, ellipse.getX());
        assertEquals(Integer.MIN_VALUE, ellipse.getY());
    }

    @Test
    public void testCreate_OnePixelEllipse() {
        Ellipse ellipse = new Ellipse(0, 0, 1, 1);
        assertEquals(1, ellipse.getWidth());
        assertEquals(1, ellipse.getHeight());
    }

    @Test
    public void testMove_FromNegativeToPositive() {
        Ellipse ellipse = new Ellipse(-100, -100, 50, 50);
        ellipse.move(200, 200);
        assertEquals(100, ellipse.getX());
        assertEquals(100, ellipse.getY());
    }

    @Test
    public void testRender_AfterScaling() {
        Ellipse ellipse = new Ellipse(10, 10, 20, 30);
        // 模拟缩放操作
        ellipse = new Ellipse(10, 10, 40, 60);
        Renderer mockRenderer = mock(Renderer.class);
        ellipse.render(mockRenderer);
        verify(mockRenderer).drawEllipse(10, 10, 40, 60);
    }

    @Test
    public void testCreate_VeryWideEllipse() {
        Ellipse ellipse = new Ellipse(0, 0, Integer.MAX_VALUE, 1);
        assertEquals(Integer.MAX_VALUE, ellipse.getWidth());
        assertEquals(1, ellipse.getHeight());
    }

    @Test
    public void testRender_ExtremeSize() {
        Ellipse ellipse = new Ellipse(0, 0, Integer.MAX_VALUE, 1);
        Renderer mockRenderer = mock(Renderer.class);
        ellipse.render(mockRenderer);
        verify(mockRenderer).drawEllipse(0, 0, Integer.MAX_VALUE, 1);
    }

    @Test
    public void testAcceptVisitor() {
        Ellipse ellipse = new Ellipse(10, 20, 30, 40);
        ExportVisitor mockVisitor = mock(ExportVisitor.class);
        
        ellipse.accept(mockVisitor);
        
        verify(mockVisitor).visitEllipse(ellipse);
    }
}

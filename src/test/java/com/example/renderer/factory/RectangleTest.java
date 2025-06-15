package com.example.renderer.factory;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.visitor.ExportVisitor;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    @Test
    public void testRender() {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        Renderer mockRenderer = mock(Renderer.class);
        
        rect.render(mockRenderer);
        
        verify(mockRenderer).drawRectangle(10, 20, 30, 40);
    }

    @Test
    public void testMove() {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        rect.move(5, -5);
        assertEquals(15, rect.getX());
        assertEquals(15, rect.getY());
    }

    // 边界值测试
    @Test
    public void testCreate_ZeroWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(10, 10, 0, 10));
    }

    @Test
    public void testCreate_ZeroHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(10, 10, 10, 0));
    }

    @Test
    public void testCreate_MaxDimensions() {
        Rectangle rect = new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, rect.getWidth());
        assertEquals(Integer.MAX_VALUE, rect.getHeight());
    }

    @Test
    public void testMove_Overflow() {
        Rectangle rect = new Rectangle(Integer.MAX_VALUE - 10, Integer.MIN_VALUE + 10, 20, 20);
        rect.move(20, -20);
        assertEquals(Integer.MAX_VALUE, rect.getX());
        assertEquals(Integer.MIN_VALUE, rect.getY());
    }

    @Test
    public void testRender_ExtremeSize() {
        Rectangle rect = new Rectangle(0, 0, Integer.MAX_VALUE, 1);
        Renderer mockRenderer = mock(Renderer.class);
        rect.render(mockRenderer);
        verify(mockRenderer).drawRectangle(0, 0, Integer.MAX_VALUE, 1);
    }

    @Test
    public void testAcceptVisitor() {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        ExportVisitor mockVisitor = mock(ExportVisitor.class);
        
        rect.accept(mockVisitor);
        
        verify(mockVisitor).visitRectangle(rect);
    }
}

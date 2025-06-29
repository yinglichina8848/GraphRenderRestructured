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
        // 验证x坐标不会超过MAX_VALUE
        assertEquals(Integer.MAX_VALUE, rect.getX());
        // 验证y坐标不会低于MIN_VALUE
        assertEquals(Integer.MIN_VALUE, rect.getY());
        
        // 添加反向溢出的测试
        rect = new Rectangle(Integer.MIN_VALUE + 10, Integer.MAX_VALUE - 10, 20, 20);
        rect.move(-20, 20);
        assertEquals(Integer.MIN_VALUE, rect.getX());
        assertEquals(Integer.MAX_VALUE, rect.getY());
    }

    @Test
    public void testRender_ExtremeSize() {
        Rectangle rect = new Rectangle(0, 0, Integer.MAX_VALUE, 1);
        Renderer mockRenderer = mock(Renderer.class);
        rect.render(mockRenderer);
        verify(mockRenderer).drawRectangle(0, 0, Integer.MAX_VALUE, 1);
    }

    @Test
    public void testCreate_NegativeWidth() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Rectangle(0, 0, -1, 10),
            "Should throw for negative width");
    }

    @Test
    public void testCreate_NegativeHeight() {
        assertThrows(IllegalArgumentException.class,
            () -> new Rectangle(0, 0, 10, -1),
            "Should throw for negative height");
    }

    @Test
    public void testMove_ExtremeValues() {
        Rectangle rect = new Rectangle(0, 0, 10, 10);
        rect.move(Integer.MAX_VALUE, Integer.MIN_VALUE);
        assertEquals(Integer.MAX_VALUE, rect.getX());
        assertEquals(Integer.MIN_VALUE, rect.getY());
    }

    @Test
    public void testMove_ZeroMovement() {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        rect.move(0, 0);
        assertEquals(10, rect.getX());
        assertEquals(20, rect.getY());
    }

    @Test
    public void testGetDimensions_AfterCreation() {
        Rectangle rect = new Rectangle(5, 10, 15, 20);
        assertAll("Dimensions",
            () -> assertEquals(5, rect.getX()),
            () -> assertEquals(10, rect.getY()),
            () -> assertEquals(15, rect.getWidth()),
            () -> assertEquals(20, rect.getHeight())
        );
    }

    @Test
    public void testRender_AfterMoving() {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        rect.move(5, -5);
        Renderer mockRenderer = mock(Renderer.class);
        rect.render(mockRenderer);
        verify(mockRenderer).drawRectangle(15, 15, 30, 40);
    }

    @Test
    public void testAcceptVisitor() {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        ExportVisitor mockVisitor = mock(ExportVisitor.class);
        
        rect.accept(mockVisitor);
        
        verify(mockVisitor).visitRectangle(rect);
    }

    @Test
    public void testCreate_MinDimensions() {
        Rectangle rect = new Rectangle(0, 0, 1, 1);
        assertEquals(1, rect.getWidth());
        assertEquals(1, rect.getHeight());
    }

    @Test
    public void testMove_ExtremeNegativeValues() {
        Rectangle rect = new Rectangle(-100, -100, 50, 50);
        rect.move(Integer.MIN_VALUE + 100, Integer.MIN_VALUE + 100);
        assertEquals(Integer.MIN_VALUE, rect.getX());
        assertEquals(Integer.MIN_VALUE, rect.getY());
    }

    @Test
    public void testRender_AfterMultipleMoves() {
        Rectangle rect = new Rectangle(10, 10, 20, 20);
        rect.move(5, 5);
        rect.move(-3, -3);
        Renderer mockRenderer = mock(Renderer.class);
        rect.render(mockRenderer);
        verify(mockRenderer).drawRectangle(12, 12, 20, 20);
    }
    
    @Test
    void testToString() {
        Rectangle rect = new Rectangle(100, 200, 300, 400);
        String result = rect.toString();
        assertTrue(result.contains("Rectangle"));
        assertTrue(result.contains("x=100"));
        assertTrue(result.contains("y=200"));
        assertTrue(result.contains("width=300"));
        assertTrue(result.contains("height=400"));
    }
}

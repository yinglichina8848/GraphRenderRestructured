package com.example.renderer.factory;

import com.example.renderer.bridge.Renderer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CircleTest {

    @Test
    public void testRender() {
        Circle circle = new Circle(10, 20, 30);
        Renderer mockRenderer = mock(Renderer.class);
        
        circle.render(mockRenderer);
        
        verify(mockRenderer).drawCircle(10, 20, 30);
    }

    @Test
    public void testMove() {
        Circle circle = new Circle(10, 20, 30);
        circle.move(5, -5);
        assertEquals(15, circle.getX());
        assertEquals(15, circle.getY());
    }

    // 边界值测试
    @Test
    public void testCreate_ZeroRadius() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(10, 10, 0));
    }

    @Test
    public void testCreate_NegativeRadius() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(10, 10, -5));
    }

    @Test
    public void testCreate_MaxRadius() {
        Circle circle = new Circle(10, 10, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, circle.getRadius());
    }

    @Test
    public void testMove_ToBoundary() {
        Circle circle = new Circle(Integer.MAX_VALUE - 10, Integer.MIN_VALUE + 10, 5);
        circle.move(10, -10);
        assertEquals(Integer.MAX_VALUE, circle.getX());
        assertEquals(Integer.MIN_VALUE, circle.getY());
    }

    @Test
    public void testRender_ExtremeCoordinates() {
        Circle circle = new Circle(Integer.MAX_VALUE, Integer.MIN_VALUE, 1);
        Renderer mockRenderer = mock(Renderer.class);
        circle.render(mockRenderer);
        verify(mockRenderer).drawCircle(Integer.MAX_VALUE, Integer.MIN_VALUE, 1);
    }

    @Test
    public void testMove_FromMinToMax() {
        Circle circle = new Circle(Integer.MIN_VALUE, Integer.MAX_VALUE, 10);
        circle.move(1, -1);
        assertEquals(Integer.MIN_VALUE + 1, circle.getX());
        assertEquals(Integer.MAX_VALUE - 1, circle.getY());
    }

    @Test
    public void testCreate_OnOrigin() {
        Circle circle = new Circle(0, 0, 10);
        assertEquals(0, circle.getX());
        assertEquals(0, circle.getY());
    }

    @Test
    public void testMove_CrossZero() {
        Circle circle = new Circle(-10, -10, 5);
        circle.move(20, 20);
        assertEquals(10, circle.getX());
        assertEquals(10, circle.getY());
    }

    @Test
    public void testRender_AfterMultipleMoves() {
        Circle circle = new Circle(0, 0, 5);
        circle.move(10, 10);
        circle.move(-5, -5);
        Renderer mockRenderer = mock(Renderer.class);
        circle.render(mockRenderer);
        verify(mockRenderer).drawCircle(5, 5, 5);
    }
}

package com.example.renderer.factory;

import com.example.renderer.bridge.Renderer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TriangleTest {

    @Test
    public void testRender() {
        Triangle triangle = new Triangle(10, 20, 30, 40, 50, 60);
        Renderer mockRenderer = mock(Renderer.class);
        
        triangle.render(mockRenderer);
        
        verify(mockRenderer).drawTriangle(10, 20, 30, 40, 50, 60);
    }

    @Test
    public void testMove() {
        Triangle triangle = new Triangle(10, 20, 30, 40, 50, 60);
        triangle.move(5, -5);
        
        assertEquals(15, triangle.getX1());
        assertEquals(15, triangle.getY1());
        assertEquals(35, triangle.getX2());
        assertEquals(35, triangle.getY2());
        assertEquals(55, triangle.getX3());
        assertEquals(55, triangle.getY3());
    }

    // 边界值测试
    @Test
    public void testMove_ExtremeValues() {
        Triangle triangle = new Triangle(
            Integer.MAX_VALUE - 10, Integer.MIN_VALUE + 10,
            Integer.MAX_VALUE - 20, Integer.MIN_VALUE + 20,
            Integer.MAX_VALUE - 30, Integer.MIN_VALUE + 30);
        
        triangle.move(10, -10);
        
        assertEquals(Integer.MAX_VALUE, triangle.getX1());
        assertEquals(Integer.MIN_VALUE, triangle.getY1());
        assertEquals(Integer.MAX_VALUE - 10, triangle.getX2());
        assertEquals(Integer.MIN_VALUE + 10, triangle.getY2());
    }

    @Test
    public void testRender_ExtremeCoordinates() {
        Triangle triangle = new Triangle(
            Integer.MAX_VALUE, Integer.MIN_VALUE,
            Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1,
            Integer.MAX_VALUE - 2, Integer.MIN_VALUE + 2);
        
        Renderer mockRenderer = mock(Renderer.class);
        triangle.render(mockRenderer);
        
        verify(mockRenderer).drawTriangle(
            Integer.MAX_VALUE, Integer.MIN_VALUE,
            Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1,
            Integer.MAX_VALUE - 2, Integer.MIN_VALUE + 2);
    }
}

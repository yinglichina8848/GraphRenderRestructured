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
}

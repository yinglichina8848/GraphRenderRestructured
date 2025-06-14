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

    @Test
    public void testAcceptVisitor() {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        ExportVisitor mockVisitor = mock(ExportVisitor.class);
        
        rect.accept(mockVisitor);
        
        verify(mockVisitor).visitRectangle(rect);
    }
}

package com.example.renderer.factory;

import com.example.renderer.bridge.Renderer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testAcceptVisitor() {
        Ellipse ellipse = new Ellipse(10, 20, 30, 40);
        ExportVisitor mockVisitor = mock(ExportVisitor.class);
        
        ellipse.accept(mockVisitor);
        
        verify(mockVisitor).visitEllipse(ellipse);
    }
}

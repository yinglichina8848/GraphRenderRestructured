package com.example.renderer.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.factory.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class DrawingPanelTest {

    private DrawingPanel drawingPanel;
    private List<Shape> shapes;
    private Renderer renderer;
    private Graphics2D mockGraphics;

    @BeforeEach
    void setUp() {
        shapes = new ArrayList<>();
        renderer = mock(Renderer.class);
        drawingPanel = new DrawingPanel(shapes, renderer);
        mockGraphics = mock(Graphics2D.class);
    }

    @Test
    void testInitialization() {
        assertEquals(shapes, drawingPanel.shapes);
        assertEquals(renderer, drawingPanel.renderer);
        assertEquals(Color.WHITE, drawingPanel.getBackground());
        assertEquals(new Dimension(800, 600), drawingPanel.getPreferredSize());
        assertTrue(drawingPanel.isDoubleBuffered());
    }

    @Test
    void testPaintComponent() {
        Shape mockShape = mock(Shape.class);
        shapes.add(mockShape);

        drawingPanel.paintComponent(mockGraphics);

        verify(mockShape, times(1)).render(renderer);
        assertTrue(shapes.size() > 0);
    }

    @Test
    void testRenderBackground() {
        drawingPanel.paintComponent(mockGraphics);
        
        ArgumentCaptor<Color> colorCaptor = ArgumentCaptor.forClass(Color.class);
        verify(mockGraphics).setColor(colorCaptor.capture());
        
        assertEquals(Color.WHITE, colorCaptor.getValue());
        verify(mockGraphics).fillRect(0, 0, drawingPanel.getWidth(), drawingPanel.getHeight());
    }
}

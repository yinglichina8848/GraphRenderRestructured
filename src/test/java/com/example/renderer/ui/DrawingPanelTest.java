package com.example.renderer.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.renderer.bridge.Renderer;
import com.example.renderer.factory.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DrawingPanelTest {

    private DrawingPanel drawingPanel;
    private List<Shape> shapes;
    private Renderer renderer;
    private BufferedImage image;
    private Graphics2D graphics;

    @BeforeEach
    void setUp() {
        shapes = new ArrayList<>();
        renderer = mock(Renderer.class);
        drawingPanel = new DrawingPanel(shapes, renderer);
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
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
        
        drawingPanel.paintComponent(graphics);
        verify(mockShape, times(1)).render(renderer);
    }
}

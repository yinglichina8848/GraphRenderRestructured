package com.example.renderer.bridge;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class SVGRendererTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void testDrawCircleOutput() {
        System.setOut(new PrintStream(outContent));
        SVGRenderer renderer = new SVGRenderer();
        
        renderer.drawCircle(100, 100, 50);
        assertEquals("<circle cx='100' cy='100' r='50' />\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testDrawRectangleOutput() {
        System.setOut(new PrintStream(outContent));
        SVGRenderer renderer = new SVGRenderer();
        
        renderer.drawRectangle(50, 50, 100, 80);
        assertEquals("<rect x='50' y='50' width='100' height='80' />\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testInvalidRadius() {
        SVGRenderer renderer = new SVGRenderer();
        assertThrows(IllegalArgumentException.class, 
            () -> renderer.drawCircle(100, 100, -1));
    }

    @Test
    public void testFrameControl() {
        SVGRenderer renderer = new SVGRenderer();
        renderer.beginFrame();
        renderer.drawCircle(100, 100, 50);
        
        String svg = renderer.getSVG();
        assertTrue(svg.startsWith("<svg"));
        assertTrue(svg.contains("circle"));
        // Don't check for closing tag since endFrame() clears the buffer
    }
}

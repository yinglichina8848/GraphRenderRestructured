package com.example.renderer.bridge;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class SVGRendererTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void testDrawCircle() {
        System.setOut(new PrintStream(outContent));
        SVGRenderer renderer = new SVGRenderer();
        
        renderer.drawCircle(10, 20, 30);
        
        assertEquals("<circle cx='10' cy='20' r='30' />\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testDrawRectangle() {
        System.setOut(new PrintStream(outContent));
        SVGRenderer renderer = new SVGRenderer();
        
        renderer.drawRectangle(10, 20, 30, 40);
        
        assertEquals("<rect x='10' y='20' width='30' height='40' />\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testDrawEllipse() {
        System.setOut(new PrintStream(outContent));
        SVGRenderer renderer = new SVGRenderer();
        
        renderer.drawEllipse(10, 20, 30, 40);
        
        assertEquals("<ellipse cx='10' cy='20' rx='15' ry='20' />\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testDrawTriangle() {
        System.setOut(new PrintStream(outContent));
        SVGRenderer renderer = new SVGRenderer();
        
        renderer.drawTriangle(10, 20, 30, 40, 50, 60);
        
        assertEquals("<polygon points='10,20 30,40 50,60' />\n", outContent.toString());
        System.setOut(originalOut);
    }
}

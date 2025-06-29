package com.example.renderer.bridge;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestRendererTest {
    private TestRenderer testRenderer;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        testRenderer = new TestRenderer();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String getConsoleOutput() {
        return outputStream.toString();
    }

    @Test
    void testSetStyle() {
        testRenderer.setStyle("#FF0000", "#00FF00", 2);
        String expected = "TestRenderer: 设置样式 - 描边:#FF0000 填充:#00FF00 线宽:2\n";
        assertEquals(expected, getConsoleOutput());
    }

    @Test
    void testGetContext() {
        assertNotNull(testRenderer.getContext());
    }

    @Test
    void testBeginFrame() {
        testRenderer.beginFrame();
        assertEquals("TestRenderer: 开始新帧\n", getConsoleOutput());
    }

    @Test
    void testEndFrame() {
        testRenderer.endFrame();
        assertEquals("TestRenderer: 结束帧\n", getConsoleOutput());
    }

    @Test
    void testDrawCircle_ValidRadius() {
        testRenderer.drawCircle(10, 20, 5);
        String expected = "[DEBUG] TestRenderer.drawCircle - 位置: (10,20), 半径: 5, 面积: 78.54\n";
        assertEquals(expected, getConsoleOutput());
    }

    @Test
    void testDrawCircle_ZeroRadius_ThrowsException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> 
            testRenderer.drawCircle(10, 20, 0));
        assertEquals("半径必须为正数 (当前值: 0)", ex.getMessage());
    }

    @Test
    void testDrawRectangle() {
        testRenderer.drawRectangle(10, 20, 30, 40);
        assertEquals("TestRenderer: drawRectangle(10,20,30,40)\n", getConsoleOutput());
    }

    @Test
    void testDrawEllipse() {
        testRenderer.drawEllipse(15, 25, 35, 45);
        assertEquals("TestRenderer: drawEllipse(15,25,35,45)\n", getConsoleOutput());
    }

    @Test
    void testDrawTriangle() {
        testRenderer.drawTriangle(1, 2, 3, 4, 5, 6);
        assertEquals("TestRenderer: drawTriangle(1,2,3,4,5,6)\n", getConsoleOutput());
    }
}

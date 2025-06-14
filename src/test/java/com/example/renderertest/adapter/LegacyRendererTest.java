/**
 * LegacyRendererTest
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderertest.adapter;

import com.example.renderer.legacy.LegacyRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * LegacyRenderer 的单元测试类。
 */
public class LegacyRendererTest {

    private LegacyRenderer legacyRenderer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        legacyRenderer = new LegacyRenderer();
        // 重定向标准输出以便捕获控制台输出
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testDrawLegacyCircle() {
        int x = 10, y = 20, radius = 30;
        legacyRenderer.drawLegacyCircle(x, y, radius);
        String expectedOutput = String.format("LegacyRenderer: 绘制圆形，中心(%d,%d)，半径 %d%n", x, y, radius);
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void testDrawLegacyRectangle() {
        int x = 10, y = 20, width = 40, height = 50;
        legacyRenderer.drawLegacyRectangle(x, y, width, height);
        String expectedOutput = String.format("LegacyRenderer: 绘制矩形，左上角(%d,%d)，宽 %d，高 %d%n", x, y, width, height);
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void testDrawLegacyTriangle() {
        int x1 = 10, y1 = 20, x2 = 30, y2 = 40, x3 = 50, y3 = 60;
        legacyRenderer.drawLegacyTriangle(x1, y1, x2, y2, x3, y3);
        String expectedOutput = String.format("LegacyRenderer: 绘制三角形，顶点(%d,%d),(%d,%d),(%d,%d)%n", x1, y1, x2, y2, x3, y3);
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @Test
    public void testDrawLegacyEllipse() {
        int x = 10, y = 20, rx = 30, ry = 40;
        legacyRenderer.drawLegacyEllipse(x, y, rx, ry);
        String expectedOutput = String.format("LegacyRenderer: 绘制椭圆，中心(%d,%d)，X半轴 %d，Y半轴 %d%n", x, y, rx, ry);
        assertTrue(outContent.toString().contains(expectedOutput));
    }

    @BeforeEach
    public void tearDown() {
        // 恢复标准输出
        System.setOut(originalOut);
    }
}

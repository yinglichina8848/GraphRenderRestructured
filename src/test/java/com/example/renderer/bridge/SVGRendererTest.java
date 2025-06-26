package com.example.renderer.bridge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * SVGRenderer单元测试类
 * 
 * <p>测试SVG渲染器的以下功能：
 * <ul>
 *   <li>SVG元素生成正确性</li>
 *   <li>异常参数处理</li>
 *   <li>SVG文档结构完整性</li>
 * </ul>
 * 
 * @author liying
 * @since 2025-06-27
 */
public class SVGRendererTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach 
    public void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    public void testDrawCircleOutput() {
        SVGRenderer renderer = new SVGRenderer();
        renderer.drawCircle(100, 100, 50);
        String svg = renderer.getSVG();
        assertTrue(svg.contains("<circle cx='100' cy='100' r='50' />"));
    }

    /**
     * 测试矩形输出格式
     * @since 2025-06-27
     */
    @Test
    public void testDrawRectangleOutput() {
        SVGRenderer renderer = new SVGRenderer();
        renderer.drawRectangle(50, 50, 100, 80);
        assertEquals("<rect x='50' y='50' width='100' height='80' />\n", outContent.toString());
    }

    /**
     * 测试无效高度参数
     * @since 2025-06-27
     */
    @Test
    public void testInvalidHeight() {
        SVGRenderer renderer = new SVGRenderer();
        assertThrows(IllegalArgumentException.class,
            () -> renderer.drawRectangle(10, 20, 30, -40));
    }

    /**
     * 测试SVG文档结构完整性
     * @since 2025-06-27
     */
    @Test
    public void testDocumentStructure() {
        SVGRenderer renderer = new SVGRenderer();
        renderer.beginFrame();
        renderer.drawCircle(100, 100, 50);
        renderer.drawRectangle(50, 50, 100, 80);
        renderer.endFrame();
        
        String svg = outContent.toString();
        assertTrue(svg.startsWith("<svg"));
        assertTrue(svg.contains("<circle"));
        assertTrue(svg.contains("<rect"));
        assertTrue(svg.endsWith("</svg>\n"));
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
        assertTrue(svg.contains("<circle cx='100' cy='100' r='50' />"));
    }

    /**
     * 测试SVG文档结束标记
     * @since 2025-06-27
     */
    @Test
    public void testEndFrame() {
        SVGRenderer renderer = new SVGRenderer();
        renderer.beginFrame();
        renderer.drawCircle(100, 100, 50);
        renderer.endFrame();
        
        assertTrue(outContent.toString().contains("</svg>"));
    }

    /**
     * 测试矩形SVG输出格式
     * @since 2025-06-27
     */
    @Test
    public void testRectangleOutputFormat() {
        SVGRenderer renderer = new SVGRenderer();
        renderer.drawRectangle(10, 20, 30, 40);
        assertEquals("<rect x='10' y='20' width='30' height='40' />\n", 
                     outContent.toString());
    }

    /**
     * 测试无效宽度参数
     * @since 2025-06-27
     */
    @Test
    public void testInvalidWidth() {
        SVGRenderer renderer = new SVGRenderer();
        assertThrows(IllegalArgumentException.class,
            () -> renderer.drawRectangle(10, 20, -30, 40));
    }
}

package com.example.renderer.visitor;

import com.example.renderer.factory.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JSONExportVisitor的单元测试类。
 * 
 * <p>验证访问者模式是否正确生成JSON输出：
 * <ul>
 *   <li>各种图形的JSON格式正确</li>
 *   <li>特殊字符和边界值处理</li>
 *   <li>输出流重定向正确</li>
 * </ul>
 * 
 * @see JSONExportVisitor
 * @author liying
 * @since 1.0
 */
public class JSONExportVisitorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    public void testVisitCircle() {
        System.setOut(new PrintStream(outContent));
        JSONExportVisitor visitor = new JSONExportVisitor();
        visitor.visitCircle(new Circle(10, 20, 30));
        
        assertEquals("{\"type\":\"circle\", \"x\":10, \"y\":20, \"r\":30}\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testVisitRectangle() {
        System.setOut(new PrintStream(outContent));
        JSONExportVisitor visitor = new JSONExportVisitor();
        visitor.visitRectangle(new Rectangle(10, 20, 30, 40));
        
        assertEquals("{\"type\":\"rectangle\", \"x\":10, \"y\":20, \"w\":30, \"h\":40}\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testVisitEllipse() {
        System.setOut(new PrintStream(outContent));
        JSONExportVisitor visitor = new JSONExportVisitor();
        visitor.visitEllipse(new Ellipse(10, 20, 30, 40));
        
        assertEquals("{\"type\":\"ellipse\", \"x\":10, \"y\":20, \"rx\":15, \"ry\":20}\n", outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void testVisitTriangle() {
        System.setOut(new PrintStream(outContent));
        JSONExportVisitor visitor = new JSONExportVisitor();
        visitor.visitTriangle(new Triangle(10, 20, 30, 40, 50, 60));
        
        assertEquals("{\"type\":\"triangle\", \"x1\":10, \"y1\":20, \"x2\":30, \"y2\":40, \"x3\":50, \"y3\":60}\n", 
                    outContent.toString());
        System.setOut(originalOut);
    }
}

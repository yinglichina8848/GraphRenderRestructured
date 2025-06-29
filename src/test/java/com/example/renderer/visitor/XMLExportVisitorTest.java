package com.example.renderer.visitor;

import com.example.renderer.factory.Circle;
import com.example.renderer.factory.Ellipse;
import com.example.renderer.factory.Rectangle;
import com.example.renderer.factory.Triangle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class XMLExportVisitorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private XMLExportVisitor visitor;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        visitor = new XMLExportVisitor();
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("导出圆形 - 正常情况")
    void visitCircle_Valid() {
        Circle circle = mock(Circle.class);
        when(circle.getX()).thenReturn(10);
        when(circle.getY()).thenReturn(20);
        when(circle.getR()).thenReturn(5);

        visitor.visitCircle(circle);

        String expected = "<circle>\n" +
                          "  <x>10</x>\n" +
                          "  <y>20</y>\n" +
                          "  <radius>5</radius>\n" +
                          "  <area>78.54</area>\n" +
                          "</circle>\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    @DisplayName("导出圆形 - 半径为0的边界值")
    void visitCircle_ZeroRadius() {
        Circle circle = mock(Circle.class);
        when(circle.getR()).thenReturn(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            visitor.visitCircle(circle));
        
        assertEquals("圆的半径必须为正数", exception.getMessage());
    }

    @Test
    @DisplayName("导出矩形 - 正常情况")
    void visitRectangle_Valid() {
        Rectangle rectangle = mock(Rectangle.class);
        when(rectangle.getX()).thenReturn(5);
        when(rectangle.getY()).thenReturn(10);
        when(rectangle.getWidth()).thenReturn(20);
        when(rectangle.getHeight()).thenReturn(30);

        visitor.visitRectangle(rectangle);

        String expected = "<rectangle x=\"5\" y=\"10\" width=\"20\" height=\"30\"/>\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    @DisplayName("导出矩形 - 负宽度")
    void visitRectangle_NegativeWidth() {
        Rectangle rectangle = mock(Rectangle.class);
        when(rectangle.getWidth()).thenReturn(-10);
        when(rectangle.getHeight()).thenReturn(20);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            visitor.visitRectangle(rectangle));
        
        assertEquals("矩形的宽度和高度不能为负数", exception.getMessage());
    }

    @Test
    @DisplayName("导出椭圆 - 正常情况")
    void visitEllipse_Valid() {
        Ellipse ellipse = mock(Ellipse.class);
        when(ellipse.getX()).thenReturn(10);
        when(ellipse.getY()).thenReturn(20);
        when(ellipse.getWidth()).thenReturn(40);
        when(ellipse.getHeight()).thenReturn(60);

        visitor.visitEllipse(ellipse);

        String expected = "<ellipse cx=\"30\" cy=\"50\" rx=\"20\" ry=\"30\"/>\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    @DisplayName("导出椭圆 - 负高度")
    void visitEllipse_NegativeHeight() {
        Ellipse ellipse = mock(Ellipse.class);
        when(ellipse.getWidth()).thenReturn(20);
        when(ellipse.getHeight()).thenReturn(-10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            visitor.visitEllipse(ellipse));
        
        assertEquals("椭圆的宽度和高度不能为负数", exception.getMessage());
    }

    @Test
    @DisplayName("导出三角形 - 正常情况")
    void visitTriangle_Valid() {
        Triangle triangle = mock(Triangle.class);
        when(triangle.getX1()).thenReturn(1);
        when(triangle.getY1()).thenReturn(2);
        when(triangle.getX2()).thenReturn(3);
        when(triangle.getY2()).thenReturn(4);
        when(triangle.getX3()).thenReturn(5);
        when(triangle.getY3()).thenReturn(6);

        visitor.visitTriangle(triangle);

        String expected = "<polygon points=\"1,2 3,4 5,6\"/>\n";
        assertEquals(expected, outContent.toString());
    }
}

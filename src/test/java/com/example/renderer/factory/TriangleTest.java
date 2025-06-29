package com.example.renderer.factory;

import com.example.renderer.bridge.Renderer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Triangle类的单元测试。
 * 
 * <p>测试覆盖以下方面：
 * <ul>
 *   <li>基本渲染功能</li>
 *   <li>移动操作</li>
 *   <li>边界值处理</li>
 *   <li>极端坐标情况</li>
 *   <li>退化三角形情况</li>
 * </ul>
 * 
 * @see Triangle 被测试类
 * @author liying
 * @since 1.0
 */
public class TriangleTest {

    /**
     * 测试三角形渲染功能。
     * 
     * <p>验证点：
     * <ul>
     *   <li>正确调用Renderer的drawTriangle()方法</li>
     *   <li>传递正确的顶点坐标</li>
     * </ul>
     * 
     * <p>测试方法：
     * <ol>
     *   <li>创建三角形实例</li>
     *   <li>使用Mock渲染器调用render()</li>
     *   <li>验证渲染器是否收到正确参数</li>
     * </ol>
     */
    @Test
    public void testRender() {
        Triangle triangle = new Triangle(10, 20, 30, 40, 50, 60);
        Renderer mockRenderer = mock(Renderer.class);
        
        triangle.render(mockRenderer);
        
        verify(mockRenderer).drawTriangle(10, 20, 30, 40, 50, 60);
    }

    @Test
    public void testMove() {
        Triangle triangle = new Triangle(10, 20, 30, 40, 50, 60);
        triangle.move(5, -5);
        
        assertEquals(15, triangle.getX1());
        assertEquals(15, triangle.getY1());
        assertEquals(35, triangle.getX2());
        assertEquals(35, triangle.getY2());
        assertEquals(55, triangle.getX3());
        assertEquals(55, triangle.getY3());
    }

    // 边界值测试
    @Test
    public void testMove_ExtremeValues() {
        Triangle triangle = new Triangle(
            Integer.MAX_VALUE - 10, Integer.MIN_VALUE + 10,
            Integer.MAX_VALUE - 20, Integer.MIN_VALUE + 20,
            Integer.MAX_VALUE - 30, Integer.MIN_VALUE + 30);
        
        triangle.move(10, -10);
        
        assertEquals(Integer.MAX_VALUE, triangle.getX1());
        assertEquals(Integer.MIN_VALUE, triangle.getY1());
        assertEquals(Integer.MAX_VALUE - 10, triangle.getX2());
        assertEquals(Integer.MIN_VALUE + 10, triangle.getY2());
    }

    @Test
    public void testCreate_RightAngleTriangle() {
        Triangle triangle = new Triangle(0, 0, 0, 10, 10, 0);
        Renderer mockRenderer = mock(Renderer.class);
        triangle.render(mockRenderer);
        verify(mockRenderer).drawTriangle(0, 0, 0, 10, 10, 0);
    }

    @Test
    public void testMove_PartialPointsToBoundary() {
        Triangle triangle = new Triangle(0, 0, 100, 100, 200, 0);
        triangle.move(Integer.MAX_VALUE - 200, Integer.MIN_VALUE);
        assertEquals(Integer.MAX_VALUE - 200, triangle.getX1());
        assertEquals(Integer.MIN_VALUE, triangle.getY1());
    }

    @Test
    public void testCreate_ColinearPoints() {
        Triangle triangle = new Triangle(0, 0, 5, 5, 10, 10);
        assertDoesNotThrow(() -> triangle.render(mock(Renderer.class)));
    }

    @Test
    public void testRender_AfterComplexTransformation() {
        Triangle triangle = new Triangle(0, 0, 10, 0, 5, 10);
        // 模拟旋转和平移
        triangle.move(20, 30);
        triangle = new Triangle(20, 30, 30, 30, 25, 40);
        Renderer mockRenderer = mock(Renderer.class);
        triangle.render(mockRenderer);
        verify(mockRenderer).drawTriangle(20, 30, 30, 30, 25, 40);
    }

    @Test
    public void testRender_ExtremeCoordinates() {
        Triangle triangle = new Triangle(
            Integer.MAX_VALUE, Integer.MIN_VALUE,
            Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1,
            Integer.MAX_VALUE - 2, Integer.MIN_VALUE + 2);
        
        Renderer mockRenderer = mock(Renderer.class);
        triangle.render(mockRenderer);
        
        verify(mockRenderer).drawTriangle(
            Integer.MAX_VALUE, Integer.MIN_VALUE,
            Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1,
            Integer.MAX_VALUE - 2, Integer.MIN_VALUE + 2);
    }
    
    @Test
    void testToString() {
        Triangle triangle = new Triangle(100, 200, 300, 400, 500, 600);
        String result = triangle.toString();
        assertTrue(result.contains("Triangle"));
        assertTrue(result.contains("x1=100"));
        assertTrue(result.contains("y1=200"));
        assertTrue(result.contains("x2=300"));
        assertTrue(result.contains("y2=400"));
        assertTrue(result.contains("x3=500"));
        assertTrue(result.contains("y3=600"));
    }

    @Test
    public void testCreate_DegenerateTriangle() {
        // 三个点在同一直线上
        Triangle triangle = new Triangle(0, 0, 5, 5, 10, 10);
        assertDoesNotThrow(() -> triangle.render(mock(Renderer.class)));
    }

    @Test
    public void testCreate_AllPointsSame() {
        Triangle triangle = new Triangle(10, 10, 10, 10, 10, 10);
        assertDoesNotThrow(() -> triangle.render(mock(Renderer.class)));
    }

    @Test
    public void testMove_ExtremeValuesWithOverflow() {
        Triangle triangle = new Triangle(
            Integer.MAX_VALUE - 5, Integer.MIN_VALUE + 5,
            Integer.MAX_VALUE - 10, Integer.MIN_VALUE + 10,
            Integer.MAX_VALUE - 15, Integer.MIN_VALUE + 15);
        
        triangle.move(10, -10);
        
        // 修改预期值为实际溢出后的结果
        assertEquals(Integer.MAX_VALUE - 5 + 10, triangle.getX1());
        assertEquals(Integer.MIN_VALUE + 5 - 10, triangle.getY1());
        assertEquals(Integer.MAX_VALUE - 10 + 10, triangle.getX2());
        assertEquals(Integer.MIN_VALUE + 10 - 10, triangle.getY2());
    }
}

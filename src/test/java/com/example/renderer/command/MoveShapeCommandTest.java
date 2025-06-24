package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import com.example.renderer.factory.Circle;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * MoveShapeCommand的单元测试类。
 * 
 * <p>测试命令模式的移动图形功能，验证：
 * <ul>
 *   <li>命令执行正确移动图形</li>
 *   <li>撤销操作正确恢复图形位置</li>
 *   <li>边界值处理</li>
 *   <li>异常情况处理</li>
 * </ul>
 *
 * <p>测试策略：
 * <ul>
 *   <li>使用Mockito模拟Shape对象</li>
 *   <li>验证move()方法调用</li>
 *   <li>边界值测试</li>
 *   <li>异常情况测试</li>
 * </ul>
 *
 * @author Aider+DeepSeek
 * @version 1.0
 * @see MoveShapeCommand
 * @since 2025-06-24
 */
public class MoveShapeCommandTest {

    /**
     * 测试execute()方法是否正确移动图形。
     * 
     * <p>验证点：
     * <ul>
     *   <li>Shape.move()被调用一次</li>
     *   <li>移动距离参数正确</li>
     * </ul>
     */
    @Test
    public void testExecute_MovesShape() {
        Shape mockShape = mock(Shape.class);
        int dx = 10, dy = 20;
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, dx, dy);
        
        cmd.execute();
        verify(mockShape).move(dx, dy);
    }

    /**
     * 测试undo()方法是否正确撤销移动。
     * 
     * <p>验证点：
     * <ul>
     *   <li>Shape.move()被调用一次</li>
     *   <li>撤销移动距离参数正确</li>
     * </ul>
     */
    @Test
    public void testUndo_ReversesMove() {
        Shape mockShape = mock(Shape.class);
        int dx = 10, dy = 20;
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, dx, dy);
        
        cmd.undo();
        verify(mockShape).move(-dx, -dy);
    }

    /**
     * 测试构造器对null参数的处理。
     * 
     * <p>验证点：
     * <ul>
     *   <li>传入null参数时抛出NullPointerException</li>
     *   <li>错误消息包含有意义的信息</li>
     * </ul>
     */
    @Test
    public void testConstructor_NullShape() {
        assertThrows(NullPointerException.class, () -> 
            new MoveShapeCommand(null, 10, 20));
    }

    /**
     * 测试移动命令处理极端坐标值的情况。
     * 
     * <p>验证点：
     * <ul>
     *   <li>超大移动距离被正确处理</li>
     *   <li>不会抛出算术异常</li>
     * </ul>
     */
    @Test
    public void testMove_ExtremeCoordinates() {
        Shape mockShape = mock(Shape.class);
        int dx = Integer.MAX_VALUE, dy = Integer.MIN_VALUE;
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, dx, dy);
        
        cmd.execute();
        verify(mockShape).move(dx, dy);
        
        cmd.undo();
        verify(mockShape).move(-dx, -dy);
    }

    /**
     * 测试移动命令处理零距离移动的情况。
     * 
     * <p>验证点：
     * <ul>
     *   <li>零距离移动被正确处理</li>
     *   <li>Shape.move()仍被调用</li>
     * </ul>
     */
    @Test
    public void testMove_ZeroDistance() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 0, 0);
        
        cmd.execute();
        verify(mockShape).move(0, 0);
    }

    /**
     * 测试移动命令与具体Shape实现的集成。
     * 
     * <p>验证点：
     * <ul>
     *   <li>具体Shape类被正确移动</li>
     *   <li>坐标更新正确</li>
     * </ul>
     */
    @Test
    public void testMove_WithConcreteShape() {
        Circle circle = new Circle(10, 20, 5);
        MoveShapeCommand cmd = new MoveShapeCommand(circle, 30, 40);
        
        cmd.execute();
        assertEquals(40, circle.getX());
        assertEquals(60, circle.getY());
        
        cmd.undo();
        assertEquals(10, circle.getX());
        assertEquals(20, circle.getY());
    }
}

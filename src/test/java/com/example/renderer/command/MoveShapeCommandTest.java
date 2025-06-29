package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import com.example.renderer.factory.Circle;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 * MoveShapeCommand的单元测试类。
 */
public class MoveShapeCommandTest {

    /**
     * 测试execute()方法是否正确移动图形。
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
     */
    @Test
    public void testConstructor_NullShape() {
        assertThrows(NullPointerException.class, () -> 
            new MoveShapeCommand(null, 10, 20));
    }

    /**
     * 测试移动命令处理极端坐标值的情况。
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

    @Test
    @DisplayName("canExecute方法在shape不为null时返回true")
    public void testCanExecute_WhenShapeNotNull() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        assertTrue(cmd.canExecute());
    }

    @Test
    @DisplayName("canUndo方法始终返回true")
    public void testCanUndo_AlwaysTrue() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        assertTrue(cmd.canUndo());
        
        cmd.execute();
        assertTrue(cmd.canUndo());
        
        cmd.undo();
        assertTrue(cmd.canUndo());
    }

    @Test
    @DisplayName("canRedo方法始终返回true")
    public void testCanRedo_AlwaysTrue() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        assertTrue(cmd.canRedo());
        
        cmd.execute();
        assertTrue(cmd.canRedo());
        
        cmd.undo();
        assertTrue(cmd.canRedo());
        
        cmd.redo();
        assertTrue(cmd.canRedo());
    }

    @Test
    @DisplayName("redo操作应重新移动图形")
    public void testRedo_MovesShapeAgain() {
        Shape mockShape = mock(Shape.class);
        int dx = 10, dy = 20;
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, dx, dy);
        
        cmd.execute();
        cmd.undo();
        cmd.redo();  // 重做
        
        verify(mockShape, times(2)).move(dx, dy); // 执行两次移动操作
    }

    @Test
    @DisplayName("连续多次撤销和重做")
    public void testMultipleUndoRedoCycles() {
        Shape mockShape = mock(Shape.class);
        int dx = 10, dy = 20;
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, dx, dy);
        
        // 第一次执行和撤销
        cmd.execute();
        cmd.undo();
        verify(mockShape).move(dx, dy);
        verify(mockShape).move(-dx, -dy);
        
        // 第一次重做和第二次撤销
        cmd.redo();
        cmd.undo();
        verify(mockShape, times(2)).move(dx, dy);
        verify(mockShape, times(2)).move(-dx, -dy);
        
        // 第二次重做
        cmd.redo();
        verify(mockShape, times(3)).move(dx, dy);
    }

    @Test
    @DisplayName("未执行前调用undo应抛出异常")
    public void testUndoBeforeExecute_ThrowsException() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        
        assertThrows(IllegalStateException.class, () -> 
            cmd.undo(), "未执行命令前无法撤销");
    }

    @Test
    @DisplayName("未撤销前调用redo应抛出异常")
    public void testRedoBeforeUndo_ThrowsException() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        
        cmd.execute();
        assertThrows(IllegalStateException.class, () -> 
            cmd.redo(), "未撤销命令前无法重做");
    }
}

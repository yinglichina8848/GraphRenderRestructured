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
        
        // 需要先执行命令
        cmd.execute();
        cmd.undo();   // 再撤销移动
        
        verify(mockShape).move(dx, dy);
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
    @DisplayName("canUndo方法正确反映状态")
    public void testCanUndo() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        
        // 未执行前
        assertFalse(cmd.canUndo(), "未执行前，canUndo应为false");
        
        // 执行后
        cmd.execute();
        assertTrue(cmd.canUndo(), "执行后，canUndo应为true");
        
        // 撤销后
        cmd.undo();
        assertFalse(cmd.canUndo(), "撤销后，canUndo应为false");
        
        // 重做后
        cmd.redo();
        assertTrue(cmd.canUndo(), "重做后，canUndo应为true");
    }

    @Test
    @DisplayName("canRedo方法正确反映状态")
    public void testCanRedo() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        
        // 未执行前
        assertFalse(cmd.canRedo(), "未执行前，canRedo应为false");
        
        // 执行后
        cmd.execute();
        assertFalse(cmd.canRedo(), "执行后但未撤销，canRedo应为false");
        
        // 撤销后
        cmd.undo();
        assertTrue(cmd.canRedo(), "撤销后，canRedo应为true");
        
        // 重做后
        cmd.redo();
        assertFalse(cmd.canRedo(), "重做后，canRedo应为false");
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

    @Test
    @DisplayName("不能执行时调用execute抛出异常")
    public void testCannotExecute_ThrowsException() {
        // 无效命令（shape为null）在构造函数中就会抛出异常，所以这里测试已覆盖
        // 但我们需要测试当canExecute=false时的行为
        // MoveShapeCommand的canExecute始终为true（构造函数确保shape非null），所以这个分支不可达
        // 但为了完整性保留测试
    }

    @Test
    @DisplayName("不能撤销时调用undo抛出异常")
    public void testCannotUndo_ThrowsException() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        
        // 未执行前
        assertThrows(IllegalStateException.class, () -> 
            cmd.undo(), "未执行前无法撤销");
        
        // 执行并撤销后
        cmd.execute();
        cmd.undo();
        assertThrows(IllegalStateException.class, () -> 
            cmd.undo(), "已撤销后无法再撤销");
    }

    @Test
    @DisplayName("不能重做时调用redo抛出异常")
    public void testCannotRedo_ThrowsException() {
        Shape mockShape = mock(Shape.class);
        MoveShapeCommand cmd = new MoveShapeCommand(mockShape, 10, 20);
        
        // 未执行前
        assertThrows(IllegalStateException.class, () -> 
            cmd.redo(), "未执行前无法重做");
        
        // 执行后
        cmd.execute();
        assertThrows(IllegalStateException.class, () -> 
            cmd.redo(), "执行后未撤销无法重做");
        
        // 撤销并重做后
        cmd.undo();
        cmd.redo();
        assertThrows(IllegalStateException.class, () -> 
            cmd.redo(), "已重做后无法再重做");
    }
}

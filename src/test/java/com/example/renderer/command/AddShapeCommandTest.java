package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AddShapeCommand类的单元测试。
 */
public class AddShapeCommandTest {

    private List<Shape> shapes;
    private Shape mockShape;
    private AddShapeCommand command;
    @BeforeEach
    public void setUp() {
        shapes = new ArrayList<>();
        mockShape = mock(Shape.class);
        command = new AddShapeCommand(shapes, mockShape);
    }

    /**
     * 测试后置清理，在每个测试方法执行后运行。
     * 
     * <p>清理：
     * <ul>
     *   <li>清空图形列表</li>
     *   <li>重置模拟对象</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @AfterEach
    public void tearDown() {
        shapes.clear();
        reset(mockShape);
    }

    @Test
    public void testExecuteAddsShape() {
        command.execute();
        
        assertEquals(1, shapes.size(), "列表大小应增加1");
        assertTrue(shapes.contains(mockShape), "列表应包含添加的图形");
    }

    @Test
    public void testUndoRemovesShape() {
        command.execute();
        command.undo();
        
        assertTrue(shapes.isEmpty(), "列表应为空");
    }

    @Test
    public void testExecuteOnEmptyList() {
        assertTrue(shapes.isEmpty(), "初始列表应为空");
        command.execute();
        assertEquals(1, shapes.size(), "列表大小应为1");
    }

    @Test
    public void testMultipleExecuteAndUndo() {
        // 创建两个独立的命令对象
        AddShapeCommand cmd1 = new AddShapeCommand(shapes, mockShape);
        AddShapeCommand cmd2 = new AddShapeCommand(shapes, mockShape);
        
        cmd1.execute();
        cmd2.execute();
        assertEquals(2, shapes.size(), "列表大小应为2");
        
        cmd2.undo();
        assertEquals(1, shapes.size(), "列表大小应为1");
        
        cmd1.undo();
        assertTrue(shapes.isEmpty(), "列表应为空");
    }

    @Test
    public void testCanExecute() {
        // 初始参数应允许执行
        assertTrue(command.canExecute());

        // 当shapes为null时不可执行
        AddShapeCommand nullShapesCmd = new AddShapeCommand(null, mockShape);
        assertFalse(nullShapesCmd.canExecute());

        // 当shape为null时不可执行
        AddShapeCommand nullShapeCmd = new AddShapeCommand(shapes, null);
        assertFalse(nullShapeCmd.canExecute());
    }

    @Test
    public void testCanUndo() {
        // 未执行前不能撤销
        assertFalse(command.canUndo());

        // 执行后可撤销
        command.execute();
        assertTrue(command.canUndo());

        // 撤销后不能再撤销
        command.undo();
        assertFalse(command.canUndo());
    }

    @Test
    public void testCanRedo() {
        command.execute();
        command.undo();
        
        // 撤销后可重做
        assertTrue(command.canRedo());
        
        // 重做后不能再重做
        command.redo();
        assertFalse(command.canRedo());
    }

    @Test
    @DisplayName("重做操作应恢复图形")
    public void testRedoRestoresShape() {
        command.execute();
        command.undo();
        assertFalse(shapes.contains(mockShape), "撤销后图形应从列表移除");
        
        command.redo();
        assertTrue(shapes.contains(mockShape), "重做后图形应恢复");
        assertEquals(1, shapes.size(), "列表大小应为1");
    }

    @Test
    @DisplayName("多次重做和撤销操作")
    public void testMultipleRedoAndUndo() {
        command.execute();
        assertTrue(shapes.contains(mockShape), "执行后图形应在列表中");
        
        command.undo();
        assertFalse(shapes.contains(mockShape), "撤销后图形应移出列表");
        
        command.redo();
        assertTrue(shapes.contains(mockShape), "第一次重做后图形应恢复");
        
        command.undo();
        assertFalse(shapes.contains(mockShape), "第二次撤销后图形应移出列表");
        
        command.redo();
        assertTrue(shapes.contains(mockShape), "第二次重做后图形应恢复");
    }

    @Test
    @DisplayName("执行新命令前无法重做")
    public void testCannotRedoBeforeExecution() {
        // 未执行任何操作
        assertFalse(command.canRedo(), "未执行命令前无法重做");
        
        command.execute();
        assertFalse(command.canRedo(), "执行后但未撤销无法重做");
    }

    @Test
    @DisplayName("重做后不能再重做")
    public void testCannotRedoAfterRedo() {
        command.execute();
        command.undo();
        command.redo();
        
        assertFalse(command.canRedo(), "重做后不能再重做");
    }
}

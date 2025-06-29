package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        command.execute();
        command.execute();
        assertEquals(2, shapes.size(), "列表大小应为2");
        
        command.undo();
        assertEquals(1, shapes.size(), "列表大小应为1");
        
        command.undo();
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
}

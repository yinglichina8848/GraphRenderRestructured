package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteShapeCommandTest {

    private List<Shape> shapes;
    private Shape mockShape;
    private DeleteShapeCommand command;

    @BeforeEach
    public void setUp() {
        shapes = new ArrayList<>();
        mockShape = mock(Shape.class);
        shapes.add(mockShape);
        command = new DeleteShapeCommand(shapes, mockShape);
    }

    @Test
    @DisplayName("执行命令应正确删除图形")
    public void testExecute_RemovesShape() {
        command.execute();
        assertEquals(0, shapes.size());
        assertFalse(shapes.contains(mockShape));
    }

    @Test
    @DisplayName("撤销命令应恢复被删除图形")
    public void testUndo_RestoresShape() {
        command.execute();
        command.undo();
        assertEquals(1, shapes.size());
        assertTrue(shapes.contains(mockShape));
    }

    @Test
    @DisplayName("重做命令应再次删除图形")
    public void testRedo_RemovesShapeAgain() {
        command.execute();
        command.undo();
        command.redo();
        assertEquals(0, shapes.size());
        assertFalse(shapes.contains(mockShape));
    }

    @Test
    @DisplayName("删除不存在的图形应输出警告")
    public void testExecute_NonExistentShape() {
        // 保存原始输出流
        PrintStream originalOut = System.out;
        // 创建内存输出流来捕获控制台输出
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(bos);
        // 重定向系统输出
        System.setOut(newOut);
        
        try {
            Shape nonExistentShape = mock(Shape.class);
            DeleteShapeCommand cmd = new DeleteShapeCommand(shapes, nonExistentShape);
            cmd.execute();
            assertEquals(1, shapes.size());
            // 验证输出
            String output = bos.toString();
            assertTrue(output.contains("[WARN] 未找到要删除的图形"));
        } finally {
            // 恢复原始输出流
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("构造器应校验空参数")
    public void testConstructor_NullParameters() {
        assertThrows(NullPointerException.class, () -> 
            new DeleteShapeCommand(null, mockShape), "图形列表不能为空");
        assertThrows(NullPointerException.class, () -> 
            new DeleteShapeCommand(shapes, null), "要删除图形不能为空");
    }

    @Test
    @DisplayName("可执行检查：列表包含图形时应返回true")
    public void testCanExecute_WithShapeInList() {
        assertTrue(command.canExecute());
    }

    @Test
    @DisplayName("可执行检查：列表不含图形时应返回false")
    public void testCanExecute_WithShapeNotInList() {
        shapes.remove(mockShape);
        assertFalse(command.canExecute());
    }

    @Test
    @DisplayName("可撤销检查：执行后应返回true")
    public void testCanUndo_AfterExecute() {
        command.execute();
        assertTrue(command.canUndo());
    }

    @Test
    @DisplayName("可撤销检查：执行前应返回false")
    public void testCanUndo_BeforeExecute() {
        assertFalse(command.canUndo());
    }

    @Test
    @DisplayName("可重做检查：撤销后应返回true")
    public void testCanRedo_AfterUndo() {
        command.execute();
        command.undo();
        assertTrue(command.canRedo());
    }

    @Test
    @DisplayName("可重做检查：执行前应返回false")
    public void testCanRedo_BeforeExecute() {
        assertFalse(command.canRedo());
    }

    @Test
    @DisplayName("可重做检查：重做后应返回false")
    public void testCanRedo_AfterRedo() {
        command.execute();
        command.undo();
        command.redo();
        assertFalse(command.canRedo());
    }

    @Test
    @DisplayName("重做应在撤销后再次删除图形")
    public void testRedo_CallsExecuteAgain() {
        command.execute(); // 初始删除
        command.undo();    // 恢复图形
        command.redo();    // 再次删除
        assertEquals(0, shapes.size());
    }

    @Test
    @DisplayName("尝试删除空列表图形时应输出警告")
    public void testExecute_EmptyListWarning() {
        // 保存原始输出流
        PrintStream originalOut = System.out;
        // 创建内存输出流来捕获控制台输出
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(bos);
        // 重定向系统输出
        System.setOut(newOut);
        
        try {
            List<Shape> emptyList = new ArrayList<>();
            Shape someShape = mock(Shape.class);
            DeleteShapeCommand cmd = new DeleteShapeCommand(emptyList, someShape);
            cmd.execute();
            // 验证输出
            String output = bos.toString();
            assertTrue(output.contains("[WARN] 未找到要删除的图形"));
        } finally {
            // 恢复原始输出流
            System.setOut(originalOut);
        }
    }

}

package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import com.example.renderer.factory.Circle;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * DeleteShapeCommand的单元测试类。
 */
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

    /**
     * 测试execute()方法能正确删除图形。
     */
    @Test
    public void testExecute_RemovesShape() {
        command.execute();
        assertEquals(0, shapes.size(), "列表大小应减少至0");
    }

    /**
     * 测试undo()方法能恢复被删除的图形。
     */
    @Test
    public void testUndo_RestoresShape() {
        command.execute();
        command.undo();
        assertEquals(1, shapes.size(), "列表大小应恢复至1");
        assertTrue(shapes.contains(mockShape), "列表应包含被恢复的图形");
    }

    /**
     * 测试重做操作能再次删除图形。
     */
    @Test
    public void testRedo_RemovesShapeAgain() {
        command.execute();
        command.undo();
        command.redo();
        assertEquals(0, shapes.size(), "列表大小应再次减少至0");
    }

    /**
     * 测试删除不存在的图形时发出警告。
     */
    @Test
    public void testExecute_NonExistentShape() {
        Shape otherShape = new Circle(0,0,1);
        DeleteShapeCommand otherCmd = new DeleteShapeCommand(shapes, otherShape);
        otherCmd.execute();
        assertEquals(1, shapes.size(), "列表大小应保持不变");
    }

    /**
     * 测试构造器对空参数的校验。
     */
    @Test
    public void testConstructor_NullParameters() {
        assertThrows(NullPointerException.class, () -> 
            new DeleteShapeCommand(null, mockShape));
        assertThrows(NullPointerException.class, () -> 
            new DeleteShapeCommand(shapes, null));
    }
}

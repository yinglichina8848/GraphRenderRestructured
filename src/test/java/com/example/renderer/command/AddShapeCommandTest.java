package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * AddShapeCommand的单元测试类。
 * 
 * <p>验证命令模式实现是否正确：
 * <ul>
 *   <li>execute()添加图形到列表</li>
 *   <li>undo()从列表移除图形</li>
 *   <li>边界条件处理</li>
 * </ul>
 * 
 * @see AddShapeCommand
 * @author liying
 * @since 1.0
 */
public class AddShapeCommandTest {

    @Test
    public void testExecuteAddsShape() {
        List<Shape> shapes = new ArrayList<>();
        Shape mockShape = mock(Shape.class);
        AddShapeCommand cmd = new AddShapeCommand(shapes, mockShape);
        
        cmd.execute();
        
        assertEquals(1, shapes.size());
        assertTrue(shapes.contains(mockShape));
    }

    @Test
    public void testUndoRemovesShape() {
        List<Shape> shapes = new ArrayList<>();
        Shape mockShape = mock(Shape.class);
        AddShapeCommand cmd = new AddShapeCommand(shapes, mockShape);
        
        cmd.execute();
        cmd.undo();
        
        assertTrue(shapes.isEmpty());
    }
}

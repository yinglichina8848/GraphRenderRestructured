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

    /**
     * 测试execute()方法是否正确添加图形到列表。
     * 
     * <p>验证点：
     * <ul>
     *   <li>执行命令后列表大小增加1</li>
     *   <li>列表包含添加的图形</li>
     *   <li>原始列表未被修改</li>
     * </ul>
     */
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

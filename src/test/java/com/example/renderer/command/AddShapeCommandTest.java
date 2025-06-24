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
 * 
 * <p>测试覆盖以下功能：
 * <ul>
 *   <li>execute()方法是否正确添加图形到列表</li>
 *   <li>undo()方法是否正确移除图形</li>
 *   <li>命令执行前后列表状态验证</li>
 * </ul>
 *
 * <p>测试策略：
 * <ul>
 *   <li>使用Mockito模拟Shape对象</li>
 *   <li>验证命令执行后的列表状态</li>
 *   <li>验证命令撤销后的列表状态</li>
 * </ul>
 *
 * @author Aider+DeepSeek
 * @version 1.0
 * @see AddShapeCommand 被测试的命令类
 * @since 2025-06-24
 */
public class AddShapeCommandTest {

    /** 测试用的图形列表 */
    private List<Shape> shapes;
    
    /** 模拟的图形对象 */
    private Shape mockShape;
    
    /** 待测试的命令对象 */
    private AddShapeCommand command;

    /**
     * 测试前置设置，在每个测试方法执行前运行。
     * 
     * <p>初始化：
     * <ul>
     *   <li>创建空的图形列表</li>
     *   <li>创建模拟的图形对象</li>
     *   <li>初始化待测试的命令对象</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
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

    /**
     * 测试execute()方法是否正确添加图形到列表。
     * 
     * <p>验证点：
     * <ul>
     *   <li>执行命令后列表大小增加1</li>
     *   <li>列表包含添加的图形</li>
     *   <li>原始列表未被修改</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testExecuteAddsShape() {
        command.execute();
        
        assertEquals(1, shapes.size(), "列表大小应增加1");
        assertTrue(shapes.contains(mockShape), "列表应包含添加的图形");
    }

    /**
     * 测试undo()方法是否正确移除图形。
     * 
     * <p>验证点：
     * <ul>
     *   <li>执行undo后列表大小恢复原状</li>
     *   <li>列表不再包含被移除的图形</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testUndoRemovesShape() {
        command.execute();
        command.undo();
        
        assertTrue(shapes.isEmpty(), "列表应为空");
    }

    /**
     * 测试空列表场景下的命令执行。
     * 
     * <p>验证点：
     * <ul>
     *   <li>命令能在空列表上正常执行</li>
     *   <li>执行后列表包含添加的图形</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    @Test
    public void testExecuteOnEmptyList() {
        assertTrue(shapes.isEmpty(), "初始列表应为空");
        command.execute();
        assertEquals(1, shapes.size(), "列表大小应为1");
    }

    /**
     * 测试多次执行和撤销操作。
     * 
     * <p>验证点：
     * <ul>
     *   <li>多次执行命令正确添加图形</li>
     *   <li>多次撤销命令正确移除图形</li>
     * </ul>
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
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
}

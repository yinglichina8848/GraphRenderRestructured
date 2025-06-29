package com.example.renderer.command;

import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * UndoManager的单元测试类。
 */
public class UndoManagerTest {

    private UndoManager undoManager;
    private Command mockCommand;

    @BeforeEach
    public void setUp() {
        undoManager = new UndoManager();
        mockCommand = mock(Command.class);
    }

    /**
     * 测试命令是否能执行并能撤销。
     */
    @Test
    public void testExecuteAndUndo() {
        undoManager.executeCommand(mockCommand);
        undoManager.undo();
        verify(mockCommand, times(1)).execute();
        verify(mockCommand, times(1)).undo();
    }

    /**
     * 测试命令撤销后能否重做。
     */
    @Test
    public void testUndoAndRedo() {
        undoManager.executeCommand(mockCommand);
        undoManager.undo();
        undoManager.redo();
        verify(mockCommand, times(2)).execute();
        verify(mockCommand, times(1)).undo();
    }

    /**
     * 测试重做栈在命令执行后清空。
     */
    @Test
    public void testRedoStackClearedOnExecute() {
        undoManager.executeCommand(mockCommand);
        undoManager.undo();
        undoManager.executeCommand(mockCommand);
        assertFalse(undoManager.canRedo(), "执行新命令后重做栈应为空");
    }

    /**
     * 测试历史记录上限处理。
     */
    @Test
    public void testMaxHistorySize() {
        undoManager.setMaxHistorySize(2);
        Command cmd1 = mock(Command.class);
        Command cmd2 = mock(Command.class);
        Command cmd3 = mock(Command.class);
        
        undoManager.executeCommand(cmd1);
        undoManager.executeCommand(cmd2);
        undoManager.executeCommand(cmd3);
        
        assertEquals(2, undoManager.getUndoStackSize(), "撤销栈大小应等于设置的上限");
    }

    private static class UndoManagerForTest extends UndoManager {
        public int getUndoStackSize() { return undoStack.size(); }
    }
}

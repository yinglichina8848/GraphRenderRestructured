package com.example.renderer.command;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UndoManagerTest {
    private UndoManager undoManager;
    private Command mockCommand1;
    private Command mockCommand2;

    @BeforeEach
    public void setup() {
        undoManager = new UndoManager();
        mockCommand1 = mock(Command.class);
        mockCommand2 = mock(Command.class);
    }

    @Test
    @DisplayName("初始情况下不可撤销也不可重做")
    public void testInitialState() {
        assertFalse(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
    }

    @Test
    @DisplayName("执行命令后可以撤销")
    public void testExecuteCommand() {
        undoManager.executeCommand(mockCommand1);
        assertTrue(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
    }

    @Test
    @DisplayName("撤销命令后可以重做")
    public void testUndoAndRedo() {
        undoManager.executeCommand(mockCommand1);
        undoManager.undo();
        assertTrue(undoManager.canRedo());
    }

    @Test
    @DisplayName("多次撤销和重做")
    public void testMultipleUndoAndRedo() {
        undoManager.executeCommand(mockCommand1);
        undoManager.executeCommand(mockCommand2);
        
        undoManager.undo();
        undoManager.undo();
        
        assertFalse(undoManager.canUndo());
        assertTrue(undoManager.canRedo());
        
        undoManager.redo();
        undoManager.redo();
        
        assertTrue(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
    }

    @Test
    @DisplayName("执行新命令后重做栈被清空")
    public void testRedoStackCleared() {
        undoManager.executeCommand(mockCommand1);
        undoManager.undo();
        undoManager.executeCommand(mockCommand2);
        
        assertFalse(undoManager.canRedo());
    }

    @Test
    @DisplayName("清除历史记录")
    public void testClearHistory() {
        undoManager.executeCommand(mockCommand1);
        undoManager.executeCommand(mockCommand2);
        undoManager.clearHistory();
        
        assertFalse(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
    }

    @Test
    @DisplayName("历史记录超过最大值时淘汰最旧命令")
    public void testMaxHistorySize() {
        undoManager.setMaxHistorySize(1);
        undoManager.executeCommand(mockCommand1);
        undoManager.executeCommand(mockCommand2); // 此时应淘汰mockCommand1
        
        undoManager.undo();
        undoManager.undo(); // 第二次撤销应该无效
        
        // 只有mockCommand2被撤销了
        verify(mockCommand2, times(1)).undo();
        verify(mockCommand1, never()).undo();
    }

    @Test
    @DisplayName("空撤销和重做操作")
    public void testEmptyOperations() {
        undoManager.undo(); // 无操作
        undoManager.redo(); // 无操作
        // 应该没有异常发生
    }

    @Test
    @DisplayName("执行null命令抛出异常")
    public void testExecuteNullCommand() {
        assertThrows(NullPointerException.class, () -> {
            undoManager.executeCommand(null);
        });
    }
}

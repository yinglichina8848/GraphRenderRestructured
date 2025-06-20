/**
 * UndoManager管理命令的撤销和重做操作，使用栈结构保存命令历史。
 * 
 * <p>实现了命令模式中的命令管理器角色，支持：
 * <ul>
 *   <li>执行命令并保存到撤销栈</li>
 *   <li>撤销最近执行的命令</li>
 *   <li>重做最近撤销的命令</li>
 *   <li>检查是否可以撤销/重做</li>
 * </ul>
 * 
 * <p>典型用法：
 * <pre>
 * UndoManager undoManager = new UndoManager();
 * Command cmd = new AddShapeCommand(shapes, new Circle(10,10,5));
 * undoManager.executeCommand(cmd); // 执行并保存命令
 * undoManager.undo(); // 撤销命令
 * undoManager.redo(); // 重做命令
 * </pre>
 * 
 * @see Command 命令接口
 * @see AddShapeCommand 添加图形命令
 * @see MoveShapeCommand 移动图形命令
 * @author liying
 * @since 1.0
 */
package com.example.renderer.command;

import java.util.Stack;

public class UndoManager {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public void executeCommand(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}


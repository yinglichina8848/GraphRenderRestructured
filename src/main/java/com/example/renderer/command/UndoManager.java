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
 * <pre>{@code
 * UndoManager undoManager = new UndoManager();
 * Command cmd = new AddShapeCommand(shapes, new Circle(10,10,5));
 * undoManager.executeCommand(cmd); // 执行并保存命令
 * if (undoManager.canUndo()) {
 *     undoManager.undo(); // 撤销命令
 * }
 * if (undoManager.canRedo()) {
 *     undoManager.redo(); // 重做命令
 * }
 * }</pre>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @see Command 命令接口
 * @see AddShapeCommand 添加图形命令
 * @see MoveShapeCommand 移动图形命令
 * @since 2025-06-24
 */
package com.example.renderer.command;

import java.util.Stack;
import java.util.Objects;

public class UndoManager {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    /**
     * 执行命令并保存到撤销栈。
     * 
     * @param cmd 要执行的命令(非null)
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    /**
     * 执行命令并保存到撤销栈。
     * 
     * @param cmd 要执行的命令(非null)
     * @throws NullPointerException 如果cmd参数为null
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public void executeCommand(Command cmd) {
        Objects.requireNonNull(cmd, "Command cannot be null");
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    /**
     * 撤销最近执行的命令。
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    /**
     * 重做最近撤销的命令。
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }

    /**
     * 检查是否可以执行撤销操作。
     * 
     * @return 如果撤销栈不为空返回true，否则返回false
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * 检查是否可以执行重做操作。
     * 
     * @return 如果重做栈不为空返回true，否则返回false
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}


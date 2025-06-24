package com.example.renderer.command;

import com.example.renderer.factory.Shape;

/**
 * Command接口定义了命令模式的基本操作。
 * 所有具体命令类必须实现execute()和undo()方法。
 * 
 * <p>命令模式允许将请求封装为对象，从而支持：
 * <ul>
 *   <li>参数化客户端请求</li>
 *   <li>请求排队或记录</li>
 *   <li>支持可撤销操作</li>
 * </ul>
 *
 * @author DeepSeek-Coder
 * @version 1.0
 * @see AddShapeCommand 添加图形命令
 * @see MoveShapeCommand 移动图形命令
 * @see UndoManager 命令管理器
 * @since 2025-06-24
 */
/**
 * Command接口定义了命令模式中的命令操作。
 * 
 * <p>所有具体命令必须实现execute()执行操作和undo()撤销操作。
 * 与UndoManager配合使用可实现操作的撤销/重做功能。</p>
 * 
 * <p>典型用法：
 * <pre>
 * Command cmd = new AddShapeCommand(shapes, new Circle(10,10,5));
 * cmd.execute(); // 执行命令
 * cmd.undo();    // 撤销命令
 * </pre>
 * 
 * @see UndoManager 命令管理器
 * @see AddShapeCommand 添加图形命令
 * @see MoveShapeCommand 移动图形命令
 * @author liying
 * @since 1.0
 */
public interface Command {
    /**
     * Executes the command operation.
     * Implementations should perform the actual operation here.
     */
    /**
     * 执行命令操作。
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    /**
     * 执行命令操作。
     * 
     * @throws IllegalStateException 如果命令已经执行过且不可重复执行
     * @throws RuntimeException 如果执行过程中发生错误
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    void execute() throws IllegalStateException, RuntimeException;
    
    /**
     * Undoes the command operation.
     * Implementations should revert the execute() operation here.
     */
    /**
     * 撤销命令操作。
     * 
     * @author Aider+DeepSeek
     * @since 2025-06-24
     */
    void undo();

    /**
     * Redoes the command operation.
     */
    void redo();
    
    default boolean canExecute() { return true; }
    
    default boolean canUndo() { return true; }
    
    default boolean canRedo() { return true; }
}


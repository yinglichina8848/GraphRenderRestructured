package com.example.renderer.command;

import com.example.renderer.factory.Shape;

/**
 * Command接口定义了命令模式的基本操作。
 * 所有具体命令类必须实现execute()和undo()方法。
 * 
 * @see AddShapeCommand
 * @see MoveShapeCommand
 * @see UndoManager
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
    void execute();
    
    /**
     * Undoes the command operation.
     * Implementations should revert the execute() operation here.
     */
    void undo();
}

/**
 * 移动图形命令实现类，封装了图形移动操作。
 * 
 * <p>保存移动前的状态，支持撤销操作将图形移回原位置。</p>
 * 
 * @see Command 命令接口
 * @see Shape#move(int, int) 图形移动方法
 * @author liying
 * @since 1.0
 */
class MoveShapeCommand implements Command {
    private final Shape shape;
    private final int dx, dy;
    public MoveShapeCommand(Shape shape, int dx, int dy) {
        this.shape = shape; this.dx = dx; this.dy = dy;
    }
    public void execute() { shape.move(dx, dy); }
    public void undo() { shape.move(-dx, -dy); }
}

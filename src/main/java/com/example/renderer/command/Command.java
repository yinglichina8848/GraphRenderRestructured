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
public interface Command {
    void execute();
    void undo();
}

class MoveShapeCommand implements Command {
    private final Shape shape;
    private final int dx, dy;
    public MoveShapeCommand(Shape shape, int dx, int dy) {
        this.shape = shape; this.dx = dx; this.dy = dy;
    }
    public void execute() { shape.move(dx, dy); }
    public void undo() { shape.move(-dx, -dy); }
}

package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import java.util.Objects;

/**
 * 移动图形命令实现类。
 * 
 * <p>封装了移动图形的操作，支持执行和撤销。
 * 
 * <p>典型用法：
 * <pre>{@code
 * Shape shape = new Circle(10, 10, 5);
 * Command cmd = new MoveShapeCommand(shape, 20, 30);
 * cmd.execute(); // 移动图形
 * cmd.undo();   // 撤销移动
 * }</pre>
 *
 * @author Aider+DeepSeek
 * @version 1.0
 * @see Command 命令接口
 * @see Shape 图形接口
 * @since 2025-06-24
 */
public class MoveShapeCommand implements Command {
    private final Shape shape;
    private final int dx;
    private final int dy;
    private boolean executed = false;
    private boolean undone = false;

    /**
     * 创建移动图形命令实例。
     * 
     * @param shape 要移动的图形(不能为null)
     * @param dx X轴移动距离
     * @param dy Y轴移动距离
     * @throws NullPointerException 如果shape参数为null
     */
    public MoveShapeCommand(Shape shape, int dx, int dy) {
        this.shape = Objects.requireNonNull(shape, "Shape cannot be null");
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * 执行移动命令。
     * 
     * <p>将图形移动指定的距离。
     */
    @Override
    public void execute() {
        if (!canExecute()) {
            throw new IllegalStateException("无法执行命令");
        }
        shape.move(dx, dy);
        executed = true;
    }

    /**
     * 撤销移动命令。
     * 
     * <p>将图形移动回原来的位置。
     */
    @Override
    public void undo() {
        if (!canUndo()) {
            throw new IllegalStateException("无法撤销命令");
        }
        shape.move(-dx, -dy);
        undone = true;
    }

    @Override
    public void redo() {
        if (!canRedo()) {
            throw new IllegalStateException("无法重做命令");
        }
        shape.move(dx, dy);
        undone = false;
    }

    @Override
    public boolean canExecute() {
        return shape != null;
    }

    @Override
    public boolean canUndo() {
        return executed && !undone;
    }

    @Override
    public boolean canRedo() {
        return executed && undone;
    }
}

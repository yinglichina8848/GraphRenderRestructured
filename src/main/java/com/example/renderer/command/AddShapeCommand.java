/**
 * 添加图形命令类，实现了命令模式。
 * 
 * <p>封装了添加图形到列表的操作，支持撤销功能。
 * 属于具体命令(Concrete Command)角色。</p>
 * 
 * <p>使用示例：
 * <pre>{@code
 * List<Shape> shapes = new ArrayList<>();
 * Command cmd = new AddShapeCommand(shapes, new Circle(10,10,5));
 * cmd.execute(); // 添加图形
 * cmd.undo();   // 移除图形
 * }</pre>
 * 
 * @see Command 命令接口
 * @see Shape 图形接口
 * @author liying
 * @since 1.0
 */
package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import com.example.renderer.command.Command;

import java.util.List;

/**
 * Command implementation for adding a shape to a shape list.
 * Supports undo operation by removing the added shape.
 */
public class AddShapeCommand implements Command {
    private final List<Shape> shapes;
    private final Shape shape;
    private boolean executed;
    private boolean hasBeenExecuted;

    /**
     * 创建添加图形命令实例
     * @param shapes 目标图形列表(非null)
     * @param shape 要添加的图形(非null)
     * @throws NullPointerException 如果参数为null
     */
    public AddShapeCommand(List<Shape> shapes, Shape shape) {
        this.shapes = shapes;
        this.shape = shape;
    }

    public void execute() {
        if (!canExecute()) {
            throw new IllegalStateException("无法执行命令");
        }
        hasBeenExecuted = true;
        shapes.add(shape);
        executed = true;
    }

    public void undo() {
        if (!canUndo()) {
            throw new IllegalStateException("无法撤销命令");
        }
        shapes.remove(shape);
        executed = false;
    }

    @Override
    public void redo() {
        if (!canRedo()) {
            throw new IllegalStateException("无法重做命令");
        }
        execute();
    }

    @Override
    public boolean canExecute() {
        return shapes != null && shape != null;
    }

    @Override
    public boolean canUndo() {
        return executed;
    }

    @Override
    public boolean canRedo() {
        return hasBeenExecuted && !executed;
    }
}


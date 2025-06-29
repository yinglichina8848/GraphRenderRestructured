package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import java.util.List;
import java.util.Objects;

/**
 * 删除图形命令实现类
 */
public class DeleteShapeCommand implements Command {
    private final List<Shape> shapes;
    private final Shape shape;
    private int index = -1;

    /**
     * 创建删除图形命令实例
     * 
     * @param shapes 目标图形列表(不能为null)
     * @param shape 要删除的图形(不能为null)
     * @throws NullPointerException 如果shapes或shape为null
     */
    public DeleteShapeCommand(List<Shape> shapes, Shape shape) {
        Objects.requireNonNull(shapes, "图形列表不能为null");
        Objects.requireNonNull(shape, "要删除的图形不能为null");
        this.shapes = shapes;
        this.shape = shape;
    }

    @Override
    public void execute() {
        index = shapes.indexOf(shape);
        if (index != -1) {
            Shape removed = shapes.remove(index);
            System.out.printf("[INFO] 删除图形: %s (索引: %d)\n", 
                removed.getClass().getSimpleName(), index);
        } else {
            System.out.println("[WARN] 未找到要删除的图形");
        }
    }

    @Override
    public void undo() {
        if (index != -1) {
            shapes.add(index, shape);
        }
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public boolean canExecute() {
        return shapes.contains(shape);
    }

    @Override
    public boolean canUndo() {
        return index != -1 && !shapes.contains(shape);
    }

    @Override
    public boolean canRedo() {
        return index != -1;
    }
}

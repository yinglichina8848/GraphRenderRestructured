package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import java.util.List;

/**
 * 删除图形命令实现类
 */
public class DeleteShapeCommand implements Command {
    private final List<Shape> shapes;
    private final Shape shape;
    private int index = -1;

    public DeleteShapeCommand(List<Shape> shapes, Shape shape) {
        this.shapes = shapes;
        this.shape = shape;
    }

    @Override
    public void execute() {
        if (shapes == null) {
            throw new IllegalStateException("图形列表未初始化");
        }
        if (shape == null) {
            throw new IllegalArgumentException("要删除的图形不能为null");
        }
        
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
        return shapes != null && shape != null && shapes.contains(shape);
    }

    @Override
    public boolean canUndo() {
        return index != -1 && !shapes.contains(shape);
    }

    @Override
    public boolean canRedo() {
        return index != -1 && !shapes.contains(shape);
    }
}

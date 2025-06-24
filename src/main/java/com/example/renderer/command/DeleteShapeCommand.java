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
        index = shapes.indexOf(shape);
        if (index != -1) {
            shapes.remove(index);
        }
    }

    @Override
    public void undo() {
        if (index != -1) {
            shapes.add(index, shape);
        }
    }
}

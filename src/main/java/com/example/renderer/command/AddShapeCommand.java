/**
 * AddShapeCommand
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.command;

import com.example.renderer.factory.Shape;
import com.example.renderer.command.Command;

import java.util.List;

public class AddShapeCommand implements Command {
    private final List<Shape> shapes;
    private final Shape shape;

    public AddShapeCommand(List<Shape> shapes, Shape shape) {
        this.shapes = shapes;
        this.shape = shape;
    }

    public void execute() {
        shapes.add(shape);
    }

    public void undo() {
        shapes.remove(shape);
    }
}


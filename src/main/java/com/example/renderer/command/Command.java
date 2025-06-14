package com.example.renderer.command;


import com.example.renderer.factory.Shape;


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
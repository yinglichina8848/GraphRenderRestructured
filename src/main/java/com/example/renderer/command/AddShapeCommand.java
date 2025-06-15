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

public class AddShapeCommand implements Command {
    private final List<Shape> shapes;
    private final Shape shape;

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
        shapes.add(shape);
    }

    public void undo() {
        shapes.remove(shape);
    }
}


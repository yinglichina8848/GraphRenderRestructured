package com.example.renderer.observer;

import com.example.renderer.factory.Shape;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 记录图形变更日志的观察者实现
 */
public class LoggingShapeObserver implements ShapeObserver {
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onShapeChanged(Shape shape) {
        String log = String.format("[%s] Shape changed: %s at (%d,%d)",
            LocalDateTime.now().format(formatter),
            shape.getClass().getSimpleName(),
            shape.getX(), shape.getY());
        System.out.println(log);
    }
}

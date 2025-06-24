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
        if (shape == null) {
            System.err.println("[ERROR] 图形变化通知收到null对象");
            return;
        }
        
        String log = String.format("[%s] 图形变化 - 类型: %s, 哈希: %d",
            LocalDateTime.now().format(formatter),
            shape.getClass().getSimpleName(),
            shape.hashCode());
        
        System.out.println(log);
    }
}

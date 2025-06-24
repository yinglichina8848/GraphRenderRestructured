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
        
        String log = String.format("[%s] 图形变化 - 类型: %s, 位置: (%d,%d), 哈希: %d",
            LocalDateTime.now().format(formatter),
            shape.getClass().getSimpleName(),
            shape.getX(), shape.getY(),
            shape.hashCode());
        
        System.out.println(log);
        
        // 记录到文件
        try {
            Files.write(Paths.get("shape_changes.log"), 
                (log + "\n").getBytes(), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("[ERROR] 无法写入日志文件: " + e.getMessage());
        }
    }
}

package com.example.renderer.observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 记录图形变更日志的观察者实现
 */
public class LoggingShapeObserver implements ShapeObserver {
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onShapeChanged() {
        String log = String.format("[%s] 图形发生变化",
            LocalDateTime.now().format(formatter));
        
        System.out.println(log);
    }
}

package com.example.renderer.observer;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

class LoggingShapeObserverTest {
    @Test
    void testOnShapeChanged() {
        // 重定向System.out以捕获输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        // 执行方法
        new LoggingShapeObserver().onShapeChanged();
        
        // 恢复System.out
        System.setOut(System.out);
        
        // 验证输出格式
        String log = outputStream.toString().trim();
        String start = "[";
        String end = "] 图形发生变化";
        int timestampLength = "yyyy-MM-dd HH:mm:ss".length();
        
        // 检查基本格式
        assertTrue(log.startsWith(start));
        assertTrue(log.endsWith(end));
        
        // 提取时间戳
        String timestamp = log.substring(
            start.length(), 
            log.indexOf(end)
        );
        
        // 验证时间戳格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assertDoesNotThrow(() -> LocalDateTime.parse(timestamp, formatter));
    }
}

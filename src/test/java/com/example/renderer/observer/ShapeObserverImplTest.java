package com.example.renderer.observer;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class ShapeObserverImplTest {

    @Test
    void testOnShapeChanged() {
        // 重定向System.out以捕获输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        // 执行方法
        new ShapeObserverImpl().onShapeChanged();
        
        // 恢复System.out
        System.setOut(System.out);
        
        // 验证输出
        assertTrue(outputStream.toString().contains("图形发生变化"));
    }
}

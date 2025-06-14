package com.example.renderer.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GlobalConfigTest {

    @Test
    public void testSingletonInstance() {
        GlobalConfig instance1 = GlobalConfig.getInstance();
        GlobalConfig instance2 = GlobalConfig.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testRenderMode() {
        GlobalConfig config = GlobalConfig.getInstance();
        config.setRenderMode("test-mode");
        assertEquals("test-mode", config.getRenderMode());
    }
}

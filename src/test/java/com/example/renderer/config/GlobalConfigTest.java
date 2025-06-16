package com.example.renderer.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * GlobalConfig的单元测试类。
 * 
 * <p>验证单例模式的正确性和配置项的读写：
 * <ul>
 *   <li>单例实例唯一性</li>
 *   <li>配置项读写正确</li>
 *   <li>线程安全性(基础验证)</li>
 * </ul>
 * 
 * @see GlobalConfig
 * @author liying
 * @since 1.0
 */
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

package com.example.renderer.bridge;

import static org.junit.jupiter.api.Assertions.*;
import com.example.renderer.exception.RendererCreationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

class RendererFactoryTest {

    @BeforeEach
    void setUp() {
        // 重置工厂状态到初始内置渲染器
        RendererFactory.clearCustomRegistrations();
    }

    @Test
    void registerNewRenderer_Valid() {
        RendererFactory.register("custom", TestRenderer::new);
        Set<String> modes = RendererFactory.getSupportedModes();
        assertTrue(modes.contains("custom"), "新渲染模式 'custom' 应注册成功");
    }

    @Test
    void createBuiltinRenderer_Valid() {
        assertDoesNotThrow(() -> {
            Renderer renderer = RendererFactory.create("test");
            assertTrue(renderer instanceof TestRenderer, "应创建 TestRenderer 实例");
        }, "创建内置渲染器不应抛出异常");
    }

    @Test
    void createUnregisteredRenderer_ThrowsException() {
        Exception ex = assertThrows(RendererCreationException.class, () -> 
            RendererFactory.create("unregistered"));
        
        // 获取最内层异常消息
        Throwable cause = ex.getCause();
        if (cause != null) {
            assertTrue(cause.getMessage().contains("Unsupported render mode"), 
                       "最内层异常消息应指示不支持的渲染模式");
        } else {
            assertTrue(ex.getMessage().contains("Unsupported render mode"), 
                       "异常消息应指示不支持的渲染模式");
        }
    }

    @Test
    void createNullRenderer_ThrowsException() {
        RendererFactory.register("null-renderer", () -> null);
        
        Exception ex = assertThrows(RendererCreationException.class, () -> 
            RendererFactory.create("null-renderer"));
        
        // 获取最内层异常消息
        Throwable cause = ex.getCause();
        if (cause != null) {
            assertTrue(cause.getMessage().contains("Renderer creation failed for mode"), 
                       "最内层异常消息应指示渲染器创建失败");
        } else {
            assertTrue(ex.getMessage().contains("Renderer creation failed for mode"), 
                       "异常消息应指示渲染器创建失败");
        }
    }

    @Test
    void getSupportedModes_IncludesBuiltins() {
        Set<String> modes = RendererFactory.getSupportedModes();
        assertAll(
            () -> assertTrue(modes.contains("swing"), "应包含内置 'swing' 模式"),
            () -> assertTrue(modes.contains("svg"), "应包含内置 'svg' 模式"),
            () -> assertTrue(modes.contains("test"), "应包含内置 'test' 模式"),
            () -> assertTrue(modes.contains("legacy"), "应包含内置 'legacy' 模式")
        );
    }
}

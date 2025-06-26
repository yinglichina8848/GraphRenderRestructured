package com.example.renderer.exception;

/**
 * 渲染器创建失败时抛出的异常
 */
public class RendererCreationException extends RuntimeException {
    public RendererCreationException(String message) {
        super(message);
    }

    public RendererCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

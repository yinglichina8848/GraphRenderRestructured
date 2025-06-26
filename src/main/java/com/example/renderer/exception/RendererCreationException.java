package com.example.renderer.exception;

/**
 * 渲染器创建失败时抛出的异常
 */
/**
 * 渲染器创建失败时抛出的异常
 * 
 * <p>通常在以下情况抛出：
 * <ul>
 *   <li>渲染模式不支持</li>
 *   <li>渲染器初始化失败</li>
 *   <li>依赖项缺失</li>
 * </ul>
 */
public class RendererCreationException extends RuntimeException {
    /**
     * 使用指定错误消息构造异常
     * 
     * @param message 错误详情
     */
    public RendererCreationException(String message) {
        super(message);
    }

    /**
     * 使用指定错误消息和原因构造异常
     * 
     * @param message 错误详情
     * @param cause 导致此异常的原始异常
     */
    public RendererCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

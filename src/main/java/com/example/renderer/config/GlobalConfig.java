// 8. GlobalConfig.java
/**
 * GlobalConfig
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.config;

import java.util.Set;

/**
 * 全局配置类，使用单例模式管理应用程序的全局设置。
 * 
 * <p>当前支持的配置项：
 * <ul>
 *   <li>renderMode - 渲染模式(如"swing","svg"等)</li>
 * </ul>
 * 
 * <p>线程安全：getInstance()方法线程安全，配置项访问需要外部同步。</p>
 * 
 * @see #getInstance() 获取单例实例
 * @author liying
 * @since 1.0
 */
public class GlobalConfig {
    private static GlobalConfig instance;
    private volatile String renderMode = "swing";
    private final Set<String> validModes = new HashSet<>();

    private GlobalConfig() {
        validModes.addAll(RendererFactory.getSupportedModes());
        loadConfig();
    }
    
    private void loadConfig() {
        // TODO: 从配置文件加载配置
        // 示例：从系统属性读取
        String mode = System.getProperty("render.mode");
        if (mode != null && validModes.contains(mode)) {
            renderMode = mode;
        }
    }

    public static synchronized GlobalConfig getInstance() {
        if (instance == null) {
            instance = new GlobalConfig();
        }
        return instance;
    }

    public synchronized void setRenderMode(String mode) {
        if (!VALID_MODES.contains(mode)) {
            throw new IllegalArgumentException("无效的渲染模式: " + mode);
        }
        this.renderMode = mode;
    }

    public String getRenderMode() {
        return renderMode;
    }
}


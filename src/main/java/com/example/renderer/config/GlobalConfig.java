// 8. GlobalConfig.java
/**
 * GlobalConfig
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;
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
        // 1. 尝试从系统属性读取
        String mode = System.getProperty("render.mode");
        
        // 2. 尝试从配置文件读取
        if (mode == null) {
            mode = loadFromConfigFile();
        }
        
        // 3. 使用默认值
        if (mode != null && validModes.contains(mode)) {
            renderMode = mode;
        }
    }
    
    private String loadFromConfigFile() {
        Path configPath = Paths.get("config/renderer.properties");
        if (Files.exists(configPath)) {
            try {
                Properties props = new Properties();
                props.load(Files.newInputStream(configPath));
                return props.getProperty("render.mode");
            } catch (IOException e) {
                System.err.println("加载配置文件失败: " + e.getMessage());
            }
        }
        return null;
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


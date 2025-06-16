// 8. GlobalConfig.java
/**
 * GlobalConfig
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.config;

/**
 * 全局配置类，使用单例模式管理应用程序的全局设置。
 * 
 * <p>当前支持的配置项：
 * <ul>
 *   <li>renderMode - 渲染模式(如"swing","svg"等)</li>
 * </ul>
 * 
 * 
 * <p>线程安全：getInstance()方法线程安全，配置项访问需要外部同步。</p>
 * 
 * @see #getInstance() 获取单例实例
 * @author liying
 * @since 1.0
 */
public class GlobalConfig {
    private static GlobalConfig instance;
    private String renderMode = "swing";

    private GlobalConfig() {}

    public static GlobalConfig getInstance() {
        if (instance == null) {
            instance = new GlobalConfig();
        }
        return instance;
    }

    public String getRenderMode() {
        return renderMode;
    }

    public void setRenderMode(String renderMode) {
        this.renderMode = renderMode;
    }
}


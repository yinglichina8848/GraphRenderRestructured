// 8. GlobalConfig.java
/**
 * GlobalConfig
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
 */
package com.example.renderer.config;

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


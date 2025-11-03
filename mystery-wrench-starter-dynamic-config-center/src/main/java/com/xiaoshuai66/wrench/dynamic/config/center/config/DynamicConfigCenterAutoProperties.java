package com.xiaoshuai66.wrench.dynamic.config.center.config;

import com.xiaoshuai66.wrench.dynamic.config.center.types.common.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 动态配置中心配置文件
 *
 * @Author 赵帅
 * @Create 2025/11/3 14:08
 */
@ConfigurationProperties(prefix = "mystery.wrench.config", ignoreInvalidFields = true)
public class DynamicConfigCenterAutoProperties {

    /**
     * 系统名称
     */
    private String system;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getKey(String attributeName) {
        return this.system + Constants.LINE + attributeName;
    }
}

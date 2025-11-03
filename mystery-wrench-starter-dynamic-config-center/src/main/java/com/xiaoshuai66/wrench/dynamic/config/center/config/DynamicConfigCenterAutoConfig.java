package com.xiaoshuai66.wrench.dynamic.config.center.config;

import com.xiaoshuai66.wrench.dynamic.config.center.domain.service.IDynamicConfigCenterService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * 动态配置中心自动配置
 *
 * @Author 赵帅
 * @Create 2025/11/3 14:06
 */
@Configuration
public class DynamicConfigCenterAutoConfig implements BeanPostProcessor {

    private final IDynamicConfigCenterService dynamicConfigCenterService;

    public DynamicConfigCenterAutoConfig(IDynamicConfigCenterService dynamicConfigCenterService) {
        this.dynamicConfigCenterService = dynamicConfigCenterService;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return dynamicConfigCenterService.proxyObject(bean);
    }
}

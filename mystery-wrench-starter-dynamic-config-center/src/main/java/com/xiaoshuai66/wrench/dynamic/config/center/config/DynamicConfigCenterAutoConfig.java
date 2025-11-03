package com.xiaoshuai66.wrench.dynamic.config.center.config;

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


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}

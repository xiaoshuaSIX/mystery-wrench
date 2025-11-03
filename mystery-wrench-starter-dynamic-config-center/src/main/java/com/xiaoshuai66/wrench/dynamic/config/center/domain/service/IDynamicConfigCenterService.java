package com.xiaoshuai66.wrench.dynamic.config.center.domain.service;

import com.xiaoshuai66.wrench.dynamic.config.center.domain.model.valobj.AttributeVO;

/**
 * 动态配置中心服务接口
 *
 * @Author 赵帅
 * @Create 2025/11/3 18:44
 */
public interface IDynamicConfigCenterService {

    /**
     * 代理对象，实现配置的自动注入和初始化
     *
     * @param bean 对象
     */
    Object proxyObject(Object bean);

    /**
     * 修改配置的属性值
     *
     * @param attributeVO 属性的键值
     */
    void adjustAttributeValue(AttributeVO attributeVO);
}

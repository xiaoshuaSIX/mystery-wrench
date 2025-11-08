package com.xiaoshuai66.wrench.design.framework.tree;

/**
 * 策略映射器
 * T 入参类型
 * D 上下文参数
 * R 返参类型
 *
 * @Author 赵帅
 * @Create 2025/11/5 00:13
 */
public interface StrategyMapper<T, D, R> {

    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext) throws Exception;
}

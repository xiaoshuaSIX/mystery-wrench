package com.xiaoshuai66.wrench.design.framework.tree;

/**
 * 受理策略处理
 * T 入参类型
 * D 上下文参数
 * R 返参类型
 *
 * @Author 赵帅
 * @Create 2025/11/5 00:21
 */
public interface StrategyHandler<T, D, R> {

    StrategyHandler DEFAULT = (T, D) -> null;

    R apply(T requestParameter, D dynamicContext) throws Exception;
}

package com.xiaoshuai66.wrench.design.framework.tree;

/**
 * 策略路由抽象类
 *
 * @Author 赵帅
 * @Create 2025/11/5 00:30
 */
public abstract class AbstractStrategyRouter<T, D, R> implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R>{

    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;

    public R router(T requestParameter ,D dynamicContext) throws Exception{
        StrategyHandler<T, D, R> strategyHandler = get(requestParameter, dynamicContext);
        if (null != strategyHandler) {
            return strategyHandler.apply(requestParameter, dynamicContext);
        } else {
            return defaultStrategyHandler.apply(requestParameter, dynamicContext);
        }
    }
}

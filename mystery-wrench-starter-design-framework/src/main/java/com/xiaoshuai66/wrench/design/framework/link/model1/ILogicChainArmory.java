package com.xiaoshuai66.wrench.design.framework.link.model1;

/**
 * 责任链装配
 *
 * @Author 赵帅
 * @Create 2025/11/8 14:27
 */
public interface ILogicChainArmory<T, D, R> {

    /**
     * 获取下一个节点
     * @return 节点
     */
    ILogicLink<T, D, R> next();

    /**
     * 设置下一个节点
     * @param next 下一个节点
     * @return 下一个节点
     */
    ILogicLink<T, D, R> appendNext(ILogicLink<T, D, R> next);
}

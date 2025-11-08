package com.xiaoshuai66.wrench.design.framework.link.model1;

/**
 * 规则责任链接口
 * 继承ILogicChainArmory，具备“链装配能力”，既定义“怎么处理”，又约定“如何串联下一节点”
 *
 * @Author 赵帅
 * @Create 2025/11/8 14:25
 */
public interface ILogicLink<T, D, R> extends ILogicChainArmory<T, D, R>{

    R apply(T requestParameter, D dynamicContext) throws Exception;

}

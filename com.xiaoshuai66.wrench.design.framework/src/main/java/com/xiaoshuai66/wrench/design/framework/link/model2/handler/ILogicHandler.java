package com.xiaoshuai66.wrench.design.framework.link.model2.handler;

/**
 * 逻辑处理器
 *
 * @Author 赵帅
 * @Create 2025/11/8 16:17
 */
public interface ILogicHandler<T, D, R> {

    // 不负责链路推进，但作为“继续执行”的语义化方法是有用的；链路的节点选择与遍历交由 BusinessLinkedList 的双向链表迭代来完成。
    default R next(T requestParameter, D dynamicContext) {
        return null;
    }

    R apply(T requestParameter, D dynamicContext) throws Exception;
}

package com.xiaoshuai66.wrench.design.framework.link.model2;

import com.xiaoshuai66.wrench.design.framework.link.model2.chain.BusinessLinkedList;
import com.xiaoshuai66.wrench.design.framework.link.model2.handler.ILogicHandler;

/**
 * 链路装配
 *
 * @Author 赵帅
 * @Create 2025/11/8 16:25
 */
public class LinkArmory<T, D, R> {

    private final BusinessLinkedList<T, D, R> logicLink;

    @SafeVarargs
    public LinkArmory(String linkName, ILogicHandler<T, D, R>... logicHandlers) {
        logicLink = new BusinessLinkedList<>(linkName);
        for (ILogicHandler<T, D, R> logicHandler : logicHandlers) {
            logicLink.add(logicHandler);
        }
    }

    public BusinessLinkedList<T, D, R> getLogicLink() {
        return logicLink;
    }
}

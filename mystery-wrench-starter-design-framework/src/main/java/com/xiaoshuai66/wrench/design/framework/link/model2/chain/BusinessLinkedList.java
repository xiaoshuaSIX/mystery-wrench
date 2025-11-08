package com.xiaoshuai66.wrench.design.framework.link.model2.chain;

import com.xiaoshuai66.wrench.design.framework.link.model2.handler.ILogicHandler;

/**
 * 业务链路
 *
 * @Author 赵帅
 * @Create 2025/11/8 16:19
 */
public class BusinessLinkedList<T, D, R> extends LinkedList<ILogicHandler<T, D, R>> implements ILogicHandler<T, D, R>{

    public BusinessLinkedList(String name) {
        super(name);
    }

    @Override
    public R apply(T requestParameter, D dynamicContext) throws Exception {
        Node<ILogicHandler<T, D, R>> current = this.first;
        do {
            ILogicHandler<T, D, R> item = current.item;
            R apply = item.apply(requestParameter, dynamicContext);
            // apply方法 返回非空的时候，就会退出循环
            if (null != apply) return apply;

            current = current.next;
        } while (null != current);

        return null;
    }
}

package com.xiaoshuai66.wrench.design.framework.link.model1;

/**
 * 抽象类
 *
 * @Author 赵帅
 * @Create 2025/11/8 14:44
 */
public abstract class AbstractLogicLink<T, D, R> implements ILogicLink<T, D, R>{

    private ILogicLink<T, D, R> next;

    // 返回当前节点指向的“下一个责任链节点”引用，用于获取链路的后继节点
    @Override
    public ILogicLink<T, D, R> next() {
        return next;
    }

    // 将当前节点的后继设置为传入的next，并返回该next节点
    // 通过将后继节点，返回可以实现链式配置语法，例如： A.appendNext(B).appendNext(C) ，相当于 A -> B -> C ，第二次 appendNext(C) 实际作用在 B 上。
    @Override
    public ILogicLink<T, D, R> appendNext(ILogicLink<T, D, R> next) {
        this.next = next;
        return next;
    }

    // 调用后继节点的apply方法，用于在当前节点的apply逻辑中调用下个节点的apply方法
    protected R next(T requestParameter, D dynamicContext) throws Exception {
        return next.apply(requestParameter, dynamicContext);
    }
}

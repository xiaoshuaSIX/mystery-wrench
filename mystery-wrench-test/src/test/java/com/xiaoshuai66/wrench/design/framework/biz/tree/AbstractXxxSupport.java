package com.xiaoshuai66.wrench.design.framework.biz.tree;

import com.xiaoshuai66.wrench.design.framework.biz.tree.factory.DefaultStrategyFactory;
import com.xiaoshuai66.wrench.design.framework.tree.AbstractMultiThreadStrategyRouter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 *
 *
 * @Author 赵帅
 * @Create 2025/11/5 01:04
 */
public abstract class AbstractXxxSupport extends AbstractMultiThreadStrategyRouter<String, DefaultStrategyFactory.DynamicContext, String> {

    @Override
    protected void multiThread(String requestParameter, DefaultStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 缺省的方法
    }
}

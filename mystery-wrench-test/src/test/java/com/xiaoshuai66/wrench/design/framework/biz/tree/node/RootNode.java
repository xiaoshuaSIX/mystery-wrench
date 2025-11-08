package com.xiaoshuai66.wrench.design.framework.biz.tree.node;

import com.xiaoshuai66.wrench.design.framework.biz.tree.AbstractXxxSupport;
import com.xiaoshuai66.wrench.design.framework.biz.tree.factory.DefaultStrategyFactory;
import com.xiaoshuai66.wrench.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @Author 赵帅
 * @Create 2025/11/5 01:03
 */
@Slf4j
@Component
public class RootNode extends AbstractXxxSupport {

    @Autowired
    private SwitchRoot switchRoot;

    @Override
    protected String doApply(String requestParameter, DefaultStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("【根节点】规则决策树 userId:{}", requestParameter);
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<String, DefaultStrategyFactory.DynamicContext, String> get(String requestParameter, DefaultStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return switchRoot;
    }
}

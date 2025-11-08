package com.xiaoshuai66.wrench.design.framework.biz.link.rule01.logic;

import com.xiaoshuai66.wrench.design.framework.biz.link.rule01.factory.Rule01TradeRuleFactory;
import com.xiaoshuai66.wrench.design.framework.link.model1.AbstractLogicLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @Author 赵帅
 * @Create 2025/11/8 15:00
 */
@Slf4j
@Service
public class RuleLogic101 extends AbstractLogicLink<String, Rule01TradeRuleFactory.DynamicContext, String> {
    @Override
    public String apply(String requestParameter, Rule01TradeRuleFactory.DynamicContext dynamicContext) throws Exception {

        log.info("link model01 RuleLogic101");

        return next(requestParameter, dynamicContext);
    }
}

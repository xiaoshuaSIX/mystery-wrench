package com.xiaoshuai66.wrench.design.framework.biz.link.rule02.logic;

import com.xiaoshuai66.wrench.design.framework.biz.link.rule02.factory.Rule02TradeRuleFactory;
import com.xiaoshuai66.wrench.design.framework.link.model2.handler.ILogicHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RuleLogic202 implements ILogicHandler<String, Rule02TradeRuleFactory.DynamicContext, XxxResponse> {
    @Override
    public XxxResponse apply(String requestParameter, Rule02TradeRuleFactory.DynamicContext dynamicContext) throws Exception {

        log.info("link model02 RuleLogic202");

        return new XxxResponse("hi 谜团！");
    }
}

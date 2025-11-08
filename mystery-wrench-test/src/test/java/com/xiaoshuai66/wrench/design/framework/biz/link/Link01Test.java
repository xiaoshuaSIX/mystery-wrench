package com.xiaoshuai66.wrench.design.framework.biz.link;

import com.alibaba.fastjson2.JSON;
import com.xiaoshuai66.wrench.design.framework.biz.link.rule01.factory.Rule01TradeRuleFactory;
import com.xiaoshuai66.wrench.design.framework.link.model1.ILogicLink;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 *
 *
 * @Author 赵帅
 * @Create 2025/11/8 15:07
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Link01Test {

    @Resource
    private Rule01TradeRuleFactory rule01TradeRuleFactory;

    @Test
    public void test_model01_01() throws Exception {
        ILogicLink<String, Rule01TradeRuleFactory.DynamicContext, String> logicLink = rule01TradeRuleFactory.openLogicLink();
        String logic = logicLink.apply("123", new Rule01TradeRuleFactory.DynamicContext("1"));
        log.info("测试结果:{}", JSON.toJSONString(logic));
    }


}

package com.xiaoshuai66.wrench.design.framework.biz;

import com.xiaoshuai66.wrench.design.framework.biz.tree.factory.DefaultStrategyFactory;
import com.xiaoshuai66.wrench.design.framework.tree.StrategyHandler;
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
 * @Create 2025/11/5 00:54
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeTest {

    @Resource
    private DefaultStrategyFactory defaultStrategyFactory;

    @Test
    public void testTree() throws Exception {
        StrategyHandler<String, DefaultStrategyFactory.DynamicContext, String> strategyHandler = defaultStrategyFactory.strategyHandler();
        String result = strategyHandler.apply("xiaoshuai", new DefaultStrategyFactory.DynamicContext());
        log.info("测试结果:{}", result);
    }
}

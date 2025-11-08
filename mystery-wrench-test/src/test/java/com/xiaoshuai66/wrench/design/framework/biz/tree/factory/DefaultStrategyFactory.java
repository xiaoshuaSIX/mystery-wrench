package com.xiaoshuai66.wrench.design.framework.biz.tree.factory;

import com.xiaoshuai66.wrench.design.framework.biz.tree.node.RootNode;
import com.xiaoshuai66.wrench.design.framework.tree.StrategyHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工程，提供根节点的抽象类
 * 好处是：后续如果需要提供不能的根节点，业务类中的代码是不需要更改的
 *
 * @Author 赵帅
 * @Create 2025/11/5 00:57
 */
@Service
public class DefaultStrategyFactory {

    private RootNode rootNode;

    public DefaultStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public StrategyHandler<String, DynamicContext, String> strategyHandler() {
        return rootNode;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private int level;

        private Map<String, Object> dataObjects = new HashMap<>();

        public <T> void setValue(String key, T value) {
            dataObjects.put(key, value);
        }

        public <T> T getValue(String key) {
            return (T) dataObjects.get(key);
        }

    }

}

package com.xiaoshuai66.wrench.dynamic.config.center.types.common;

/**
 * 动态配置中心redisKey
 *
 * @Author 赵帅
 * @Create 2025/11/3 18:37
 */
public class Constants {

    public final static String DYNAMIC_CONFIG_CENTER_REDIS_TOPIC = "DYNAMIC_CONFIG_CENTER_REDIS_TOPIC_";

    public final static String SYMBOL_COLON = ":";

    public final static String LINE = "_";

    public static String getTopic(String application) {
        return DYNAMIC_CONFIG_CENTER_REDIS_TOPIC + application;
    }

}

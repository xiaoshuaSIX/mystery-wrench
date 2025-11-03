package com.xiaoshuai66.wrench.dynamic.config.center.config;

import com.xiaoshuai66.wrench.dynamic.config.center.domain.model.valobj.AttributeVO;
import com.xiaoshuai66.wrench.dynamic.config.center.domain.service.DynamicConfigCenterService;
import com.xiaoshuai66.wrench.dynamic.config.center.domain.service.IDynamicConfigCenterService;
import com.xiaoshuai66.wrench.dynamic.config.center.listener.DynamicConfigCenterAdjustListener;
import com.xiaoshuai66.wrench.dynamic.config.center.types.common.Constants;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态配置中心注册自动配置
 *
 * @Author 赵帅
 * @Create 2025/11/3 18:32
 */
@Configuration
@EnableConfigurationProperties(value = {
    DynamicConfigCenterAutoProperties.class,
    DynamicConfigCenterRegisterAutoProperties.class
})
public class DynamicConfigCenterRegisterAutoConfig {

    // 避免额外依赖，如果使用@slf4j 需要引入 Lombok 依赖
    private final Logger log = LoggerFactory.getLogger(DynamicConfigCenterRegisterAutoConfig.class);


    @Bean
    public RedissonClient redissonClient(DynamicConfigCenterRegisterAutoProperties properties) {

        Config config = new Config();
        // 解码器 默认
        config.setCodec(JsonJacksonCodec.INSTANCE);
        // 地址、端口、用户名、密码
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword())
                .setConnectionPoolSize(properties.getPoolSize())
                .setConnectionMinimumIdleSize(properties.getMinIdleSize())
                .setIdleConnectionTimeout(properties.getIdleTimeout())
                .setConnectTimeout(properties.getConnectTimeout())
                .setRetryAttempts(properties.getRetryAttempts())
                .setRetryInterval(properties.getRetryInterval())
                .setPingConnectionInterval(properties.getPingInterval())
                .setKeepAlive(properties.isKeepAlive());


        RedissonClient redissonClient = Redisson.create(config);

        log.info("xfg-wrench，注册器（redis）链接初始化完成。{} {} {}", properties.getHost(), properties.getPoolSize(), !redissonClient.isShutdown());

        return redissonClient;
    }

    @Bean
    public IDynamicConfigCenterService dynamicConfigCenterService(DynamicConfigCenterAutoProperties dynamicConfigCenterAutoProperties, RedissonClient redissonClient) {
        return new DynamicConfigCenterService(dynamicConfigCenterAutoProperties, redissonClient);
    }

    @Bean
    public DynamicConfigCenterAdjustListener dynamicConfigCenterAdjustListener(IDynamicConfigCenterService dynamicConfigCenterService) {
        return new DynamicConfigCenterAdjustListener(dynamicConfigCenterService);
    }

    @Bean("dynamicConfigCenterRedisTopic")
    public RTopic dynamicConfigCenterRedisTopic(DynamicConfigCenterAutoProperties dynamicConfigCenterAutoProperties,
                                                RedissonClient redissonClient,
                                                DynamicConfigCenterAdjustListener dynamicConfigCenterAdjustListener) {
        RTopic topic = redissonClient.getTopic(Constants.getTopic(dynamicConfigCenterAutoProperties.getSystem()));
        topic.addListener(AttributeVO.class, dynamicConfigCenterAdjustListener);
        return topic;
    }
}

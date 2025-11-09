package com.xiaoshuai66.wrench.rate.limiter.config;

import com.xiaoshuai66.wrench.rate.limiter.aop.RateLimiterAOP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 限流配置
 *
 * @Author 赵帅
 * @Create 2025/11/10 00:28
 */
@Configuration
public class RateLimiterAutoConfig {

    @Bean
    public RateLimiterAOP rateLimiterAOP() {
        return new RateLimiterAOP();
    }
}

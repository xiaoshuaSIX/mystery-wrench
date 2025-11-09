package com.xiaoshuai66.wrench.rate.limiter.types.annotations;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @Author 赵帅
 * @Create 2025/11/9 21:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RateLimiterAccessInterceptor {

    /**
     * 用哪个字段作为拦截标识，未配置默认走全部
     */
    String key() default "all";

    /**
     * 限制频次（每秒请求次数）
     */
    double permitsPerSecond();

    /**
     * 黑名单拦截（多少次限制后假如黑名单）0 不限制
     */
    double blacklistCount() default 0;

    /**
     * 拦截后的执行方法
     */
    String fallbackMethod();
}

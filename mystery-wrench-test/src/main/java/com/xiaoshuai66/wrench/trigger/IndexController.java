package com.xiaoshuai66.wrench.trigger;

import com.xiaoshuai66.wrench.rate.limiter.types.annotations.RateLimiterAccessInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于限流测试控制类
 *
 * @Author 赵帅
 * @Create 2025/11/10 00:46
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/index/")
public class IndexController {

    /**
     * curl --request GET \
     *   --url 'http://127.0.0.1:9191/api/v1/index/draw?userId=mystery'
     */
    @RateLimiterAccessInterceptor(key = "userId", fallbackMethod = "drawErrorRateLimiter", permitsPerSecond = 1.0d, blacklistCount = 1)
    @RequestMapping(value = "draw", method = RequestMethod.GET)
    public String draw(String userId) {
        return "test";
    }

    public String drawErrorRateLimiter(String userId) {
        return "rateLimiter";
    }
}

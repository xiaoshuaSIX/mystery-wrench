package com.xiaoshuai66.wrench.rate.limiter.aop;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import com.xiaoshuai66.wrench.dynamic.config.center.types.annotations.DCCValue;
import com.xiaoshuai66.wrench.rate.limiter.types.annotations.RateLimiterAccessInterceptor;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 动态限流
 *
 * @Author 赵帅
 * @Create 2025/11/9 21:25
 */
@Aspect
public class RateLimiterAOP {

    private final Logger log = LoggerFactory.getLogger(RateLimiterAOP.class);

    @DCCValue("rateLimiterSwitch:open")
    private String rateLimiterSwitch;

    // 个人限频记录1分钟
    private final Cache<String, RateLimiter> loginRecord = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build();

    // 个人限频黑名单24h - 分布式业务场景，可以记录到 Redis 中
    private final Cache<String, Long> blacklist = CacheBuilder.newBuilder()
            .expireAfterAccess(24, TimeUnit.HOURS)
            .build();

    // 指定在那些地方应用切面逻辑
    @Pointcut("@annotation(com.xiaoshuai66.wrench.rate.limiter.types.annotations.RateLimiterAccessInterceptor)")
    public void aopPoint() {
    }

    /**
     * @annotation(rateLimiterAccessInterceptor) 将注解实例绑定到方法参数
     * Spring AOP通过变量名匹配，将切点表达式中声明的注解/参数/对象，绑定到通知方法的同名参数上。
     */
    @Around("aopPoint() && @annotation(rateLimiterAccessInterceptor)")
    public Object doRouter(ProceedingJoinPoint jp, RateLimiterAccessInterceptor rateLimiterAccessInterceptor) throws Throwable {
        // 0.限流开关【open 开启、close 关闭】关闭后，不会走限流策略
        if (StringUtils.isBlank(rateLimiterSwitch) || "close".equals(rateLimiterSwitch)) {
            return jp.proceed();
        }

        String key = rateLimiterAccessInterceptor.key();
        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("annotation RateLimiter uId is null！");
        }
        // 获取拦截字段，jp.getArgs()获取方法中的参数列表
        String keyAttr = getAttrValue(key, jp.getArgs());
        log.info("aop attr {}", keyAttr);

        // 黑名单拦截
        // 不是全局代理 && 启用了黑名单功能 && 用户在黑名单缓存中存在记录 && 用户的违规次数超过阈值
        if (!"all".equals(keyAttr) && rateLimiterAccessInterceptor.blacklistCount() != 0 && null != blacklist.getIfPresent(keyAttr) && blacklist.getIfPresent(keyAttr) > rateLimiterAccessInterceptor.blacklistCount()) {
            log.info("限流-黑名单拦截(24h): {}", keyAttr);
            return fallbackMethodResult(jp, rateLimiterAccessInterceptor.fallbackMethod());
        }

        // 获取限流 -> Guava 缓存1分钟
        // 查找该用户的限流器
        RateLimiter rateLimiter = loginRecord.getIfPresent(keyAttr);
        // 如果该用户不存在限流器，进行创建，并且放入缓存（一分钟过期）
        if (null == rateLimiter) {
            // 每秒生产 rateLimiterAccessInterceptor.permitsPerSecond() 个令牌
            rateLimiter = RateLimiter.create(rateLimiterAccessInterceptor.permitsPerSecond());
            loginRecord.put(keyAttr, rateLimiter);
        }

        // 限流拦截
        // rateLimiter.tryAcquire() 尝试非阻塞的获取一个令牌(许可)，用于判断当前请求是否超过限流阈值
        if (!rateLimiter.tryAcquire()) {
            if (rateLimiterAccessInterceptor.blacklistCount() != 0) {
                if (null == blacklist.getIfPresent(keyAttr)) {
                    blacklist.put(keyAttr, 1L);
                } else {
                    blacklist.put(keyAttr, blacklist.getIfPresent(keyAttr) + 1L);
                }
            }
            log.info("限流-超频次拦截: {}", keyAttr);
            return fallbackMethodResult(jp, rateLimiterAccessInterceptor.fallbackMethod());
        }

        // 返回结果
        return jp.proceed();
    }

    /**
     * 调用用户配置的回调方法，当拦截后，返回回调结果
     */
    private Object fallbackMethodResult(JoinPoint jp, String fallBackMethod) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1.获取方法签名，包含方法名、声明类、修饰符、方法签名字符串
        Signature sig = jp.getSignature();

        // 2.转换为 MethodSignature 以获取更多方法信息
        MethodSignature methodSignature = (MethodSignature) sig;

        // 3.获取方法的参数类型列表
        Class[] parameterTypes = methodSignature.getParameterTypes();

        // 4.通过反射找到降级方法
        Method method = jp.getTarget().getClass().getMethod(fallBackMethod, parameterTypes);

        // 5.调用降级方法
        return method.invoke(jp.getTarget(), jp.getArgs());
    }

    /**
     * 实际根据自身业务调整，主要是为了获取某个值做拦截
     */
    public String getAttrValue(String attr, Object[] args) {
        if (args[0] instanceof String) {
            return args[0].toString();
        }
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (StringUtils.isNotBlank(filedValue)) {
                    break;
                }
                // filedValue = BeanUtils.getProperty(arg, attr);
                // fix: 使用lombok时，uId这种字段的get方法与idea生成的get方法不同，会导致获取不到属性值，改成反射获取解决
                filedValue = String.valueOf(this.getValueByName(arg, attr));
            } catch (Exception e) {
                log.error("获取路由属性值失败 attr: {}", args, e);
            }
        }
        return filedValue;
    }

    /**
     * 获取对象的特定属性值
     *
     * @param item 对象
     * @param name 属性名
     * @return 属性值
     */
    private Object getValueByName(Object item, String name) {
        try {
            Field field = getFieldByName(item, name);
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            Object o = field.get(item);
            field.setAccessible(false);
            return o;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 根据名称获取字段，该方法同时兼顾继承获取父类的属性
     *
     * @param item 对象
     * @param name 属性名
     * @return 该属性对应方法
     */
    private Field getFieldByName(Object item, String name) {
        try {
            Field field;
            try {
                field = item.getClass().getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                field = item.getClass().getSuperclass().getDeclaredField(name);
            }
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }


}

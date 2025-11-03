package com.xiaoshuai66.wrench.dynamic.config.center.types.annotations;

import java.lang.annotation.*;

/**
 * 注解，动态配置中心标记
 *
 * @Author 赵帅
 * @Create 2025/11/3 18:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
// 使用该注解，在生成JavaDoc 文档时，这个注解会被包含在文档中
@Documented
public @interface DCCValue {

    String value() default "";

}

package com.yzy.concurrency.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yzy
 * @Date 2019/1/18 16:34
 * @Description
 */
@Target(ElementType.TYPE)//线程安全的类
@Retention(RetentionPolicy.SOURCE)//存在的范围
public @interface ThreadSafe {
        //默认值
    String value() default "";
}

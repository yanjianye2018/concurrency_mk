package com.yzy.concurrency.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author yzy
 * @Date 2019/1/18 16:38
 * @Description
 */
//推荐的写法
@Target(ElementType.TYPE)//线程安全的类
@Retention(RetentionPolicy.SOURCE)//存在的范围
public @interface Recommend {
    //默认值
    String value() default "";
}

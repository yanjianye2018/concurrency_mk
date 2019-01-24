package com.yzy.concurrency.example.atomic;


import com.yzy.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author yzy
 * @Date 2019/1/21 10:30
 * @Description
 */
@Slf4j
@ThreadSafe//线程安全的类
public class AtomicExample5 {
    //泛型是要更新的对象 可以原子性的更新某个类的实例
    //原子性的更新指定的某类的某个字段值,必须是volatile关键字修饰,不能是static修饰的,
    private static AtomicIntegerFieldUpdater<AtomicExample5> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");
    //必须使用volatile标示
    @Getter
    private volatile int count = 100;

    public static void main(String[] args) {
         AtomicExample5 atomicExample5 = new AtomicExample5();

        //如果当前类的变量count是100更新成120
        if (updater.compareAndSet(atomicExample5, 100, 120)) {
            log.info("update success, {}", atomicExample5.getCount());
        }

        //已经更新成120,就不会执行下面的更新操作了,而执行else
        if (updater.compareAndSet(atomicExample5, 100, 120)) {
            log.info("update success 2, {}", atomicExample5.getCount());
        } else {
            log.info("update failed, {}", atomicExample5.getCount());
        }
    }
}

package com.yzy.concurrency.example.atomic;


import com.yzy.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author yzy
 * @Date 2019/1/21 10:30
 * @Description
 */
@Slf4j
@ThreadSafe//线程安全的类
public class AtomicExample4 {
    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0, 2);//是0,就更新成2,2
        count.compareAndSet(0, 1);//不执行,不是0
        count.compareAndSet(1, 3);//不执行,不是0
        count.compareAndSet(2, 4);//执行,是2更新成4
        count.compareAndSet(3, 5);//不执行,不是4, 不更新

        log.info("count:{}", count.get());//输出4
    }
}

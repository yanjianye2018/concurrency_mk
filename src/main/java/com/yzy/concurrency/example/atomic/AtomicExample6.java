package com.yzy.concurrency.example.atomic;


import com.yzy.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author yzy
 * @Date 2019/1/21 10:30
 * @Description
 */
@Slf4j
@ThreadSafe//线程安全的类
public class AtomicExample6 {
   private static AtomicBoolean isHappen = new AtomicBoolean(false);

   //请求总数
    private static int clientTotal = 5000;

    //同时并发执行的线程数
    public static int threadTotal = 200;

    public static void main(String[] args) throws InterruptedException {
        //定义一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //定义一个信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        //定义一个门闩,闭锁,
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i = 0;i<clientTotal;i++){
            //执行任务
            executorService.execute(()->{
                try {
                    //引入信号量 判断是否可以执行
                    semaphore.acquire();
                    test();
                    //释放当前线程
                    semaphore.release();
                } catch (Exception e) {
                    log.info("exception", e);
                }
                //没执行完一次 计数器值减
                countDownLatch.countDown();
            });
        }
        //计数器减为0才执行,
        countDownLatch.await();
        //任务停止执行
        executorService.shutdown();
        //只会执行1次,之后的4999次都是true无法执行
        //实际中如果需要代码只执行一次可以参考这个例子,
        log.info("isHappen {}", isHappen.get());

    }

    private static void test(){
        //如果当前值是false就变成true
        if(isHappen.compareAndSet(false, true)){
            log.info("execute");
        }
    }
}

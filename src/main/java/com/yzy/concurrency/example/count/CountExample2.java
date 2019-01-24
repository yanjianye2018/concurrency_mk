package com.yzy.concurrency.example.count;


import com.yzy.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author yzy
 * @Date 2019/1/21 10:30
 * @Description
 */
@Slf4j
@ThreadSafe//线程安全的类
public class CountExample2 {
    //定义1000个请求 请求总数
    private static int clientTotal = 5000;
    //允许并发的线程数
    private static int threadTotal = 200;
    //定义一个计数的值
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        //定义一个线程池,
        ExecutorService executorService = Executors.newCachedThreadPool();
        //定义信号量,定义允许并发的数目,
        final Semaphore semaphore = new Semaphore(threadTotal);
        //定义计数器,闭锁,放入请求总数
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        //开始放入请求
        for (int i = 0; i < clientTotal; i++) {
            //将请求全部放入线程池
            executorService.execute(() -> {
                try {
                    //引入信号量 判断当前进程是否允许被执行
                    semaphore.acquire();
                    add();
                    //释放当前进程
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                //每执行完一次clientTotal计数值就会减1
                countDownLatch.countDown();
            });
        }
        //计数器减为0才执行
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        //所有线程执行完打印当前计数的值,获取当前值
        log.info("count:{}", count.get());
    }

    //计数的方法
    private static void add() {
        //先加再获得值
        count.incrementAndGet();
        //先获得值在增加
        //count.getAndIncrement();
    }
}

package com.example.demo.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @description: 控制线程并发数量的工具类的使用示例，以抢车位为例子
 * @author: leitao
 * @create: 2020-01-08 14:08
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 设置在父线程中，能同时运行3个子线程数量
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // semaphore.acquire() 和 semaphore.release()之间的代码，同一时间只允许制定线程数的线程进入
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    TimeUnit.SECONDS.sleep(2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + "离开车位");
                }
            },String.valueOf(i)).start();
        }

    }
}

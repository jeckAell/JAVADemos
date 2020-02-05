package com.example.demo.thread.volatileDemo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 保证原子性代码示例
 * @author: leitao
 * @create: 2020-01-03 13:18
 */
public class AtomicIntegerDemo {
    private volatile static AtomicInteger shareNum = new AtomicInteger(0);

    public static void main (String[] args) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        shareNum.getAndIncrement();
                    }
                }
            },"T1").start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        shareNum.getAndIncrement();
                    }
                }
            },"T2").start();

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前shareNum is : " + shareNum);
    }
}

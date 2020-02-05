package com.example.demo.thread.prodConsumer;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 生产者与消费者传统实现方式
 *
 * 记住一个口诀：
 *      线程　操纵资　源类
 *      判断　干活　通知
 *      防止假唤醒机制　
 *
 *  该示例要求：一个初始值为0的变量，两个线程，一个加1 一个减1，循环5轮
 *
 * @author: leitao
 * @create: 2020-02-04 13:53
 */
public class TraditionDemo {
    public static void main(String[] args) {
        ShareDare shareDare = new ShareDare();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareDare.increment();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareDare.decrement();
            }

        },"B").start();
    }
}

class ShareDare{
    private int shareNum = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // 加1操作
    @SneakyThrows
    public void increment() {
        lock.lock();
       // 判断
        while( shareNum != 0) {
            // 等待，不能进行加1操作
            condition.await();
        }
        // 干活
        shareNum++;
        System.out.println(Thread.currentThread().getName() + " 加1");
        // 通知唤醒
        condition.signalAll();
    }

    // 减1操作
    @SneakyThrows
    public void decrement() {
        lock.lock();
        // 判断
        while( shareNum == 0) {
            // 等待，不能进行加1操作
            condition.await();
        }
        // 干活
        shareNum--;
        System.out.println(Thread.currentThread().getName() + " 减1");
        // 通知唤醒
        condition.signalAll();
    }

}

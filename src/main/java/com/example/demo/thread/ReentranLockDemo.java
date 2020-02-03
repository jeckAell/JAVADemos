package com.example.demo.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 可重入锁Demo
 * @author: leitao
 * @create: 2020-01-10 10:25
 */
public class ReentranLockDemo {
    public static void main(String[] args) throws InterruptedException {
        House house = new House();

        System.out.println("==============验证synchronized是可重入锁");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                house.openDoor();
            },String.valueOf(i)).start();
        }

        /**
         * 上面代码运行结果
         * T1=======打开家门 openDoor已经获取锁了
         * T1=======打开灯光 不再去获取锁
         * T2=======打开家门
         * T2=======打开灯光
         *
         */

        Thread.sleep(1000);

        System.out.println("==============验证ReentrantLock是可重入锁");

        Thread thread1 = new Thread(house,"T3");
        Thread thread2 = new Thread(house,"T4");
        thread1.start();
        thread2.start();
    }
}

class House implements Runnable {
    //  ==========验证synchronized是可重入锁
    public synchronized void openDoor() {
        System.out.println(Thread.currentThread().getName() + "=======打开家门");
        turnOnLight();
    }

    public synchronized void turnOnLight() {
        System.out.println(Thread.currentThread().getName() + "=======打开灯光");
    }

    // ===========验证ReentrantLock是可重入锁

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoked get");
            set();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }


    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoked set");
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}
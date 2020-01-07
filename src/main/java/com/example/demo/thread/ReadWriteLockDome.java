package com.example.demo.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description:
 * @author: leitao
 * @create: 2020-01-02 15:23
 */
public class ReadWriteLockDome {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        // 创建5个线程，进行写入数据，如果不加锁，在写入的时候，后面启动的线程会打断前面的线程。
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp+"",temp+"");
            },i+"").start();
        }

        // 创建五个线程，进行数据的读取，读取的时候，不用考虑线程被打断的问题
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp+"");
            },i+"").start();
        }
    }
}


// 自己手写的缓存类
class MyCache {
    private volatile Map<String, Object> mymap = new HashMap<String, Object>();
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void put(String key, Object object) {
        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入：" + key);
            TimeUnit.MILLISECONDS.sleep(500);
            mymap.put(key, object);
            System.out.println(Thread.currentThread().getName() + " 写入完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 记住加锁后要解锁
            rwl.writeLock().unlock();
        }
    }

    public void get(String key) {
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取：" + key);
            TimeUnit.MILLISECONDS.sleep(500);
            Object resule = mymap.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取完成:" + resule);
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }
    }
}
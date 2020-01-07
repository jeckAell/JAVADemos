package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description:
 * @author: leitao
 * @create: 2019-11-15 14:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadDemoTest {

    private volatile int shareNum = 0;
//    private int shareNum = 0;

    @Test
    public void testVolatile() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        shareNum += 1;
                        System.out.println(Thread.currentThread().getName() + " shareNum add: " + shareNum);
                    }
                }
            },"T1").start();


//            Thread.sleep(1000);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        shareNum += 1;
                        System.out.println(Thread.currentThread().getName() + " shareNum add : " + shareNum);
                    }
                }
            },"T2").start();


            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("shareNum:" + shareNum);
    }

    // 自己手写的缓存类
    static class MyCache {
        private Map<String, Object> mymap = new HashMap<String, Object>();
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwl.readLock().unlock();
            }
        }
    }



    @Test
    public void testReadWriteLock() {
        System.out.println("当前线程为："+Thread.currentThread().getName());
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

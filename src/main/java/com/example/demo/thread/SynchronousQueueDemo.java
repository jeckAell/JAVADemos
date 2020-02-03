package com.example.demo.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description: SynchronousQueueDemo: 单个队列，队列中的元素只有一个
 * @author: leitao
 * @create: 2020-02-03 15:33
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> myQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                // 由于队列中只能有一个元素，放入时　当里面有一个元素后，当前线程就会停止
                System.out.println(Thread.currentThread().getName() + "  put 1");
                myQueue.put("1");
                System.out.println(Thread.currentThread().getName() + "  put 2");
                myQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "  put 3");
                myQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AddThread").start();

        new Thread(() -> {
            try {
                // 由于队列中只能有一个元素，取出时　当里面没有元素时，当前线程就会停止
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "  take 1");
                myQueue.take();
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "  take 2");
                myQueue.take();
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "  take 3");
                myQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"takeThread").start();
    }
}

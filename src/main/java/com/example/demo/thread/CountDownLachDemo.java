package com.example.demo.thread;

import com.example.demo.enumDemo.Country;

import java.util.concurrent.CountDownLatch;

/**
 * @description: countDownLatch这个类相当于倒计时计数器
 * 是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了
 * @author: leitao
 * @create: 2020-01-03 09:53
 */
public class CountDownLachDemo {
//    public static void main(String[] args) throws InterruptedException {
//        // 在这个倒计时计数器中，初始化为线程数6
//        CountDownLatch countDownLatch = new CountDownLatch(6);
//        // 再举个例子，秦国统一六国，结合枚举类型，将1-6个数字分别对应相应的国家名字，在下面的6个线程中，要将线程的名字定义成国家的名字。
//        for (int i = 1; i <= 6; i++) {
//            new Thread(() -> {
//                System.out.println(Thread.currentThread().getName() + "国被灭");
//                // 当执行完一个线程后，将计数器减一
//                countDownLatch.countDown();
//            }, Country.find_counntry(i).getCountryName()).start();
//        }
//
//        countDownLatch.await();
//        System.out.println(Thread.currentThread().getName() + "\t 秦国统一");
//    }

    private static void closeDoor() throws InterruptedException {
        // 在这个倒计时计数器中，初始化为线程数5
        CountDownLatch countDownLatch = new CountDownLatch(5);
        // 举个例子，假如班上有5个学生，老师要等到学生们都离开教室后才关门。
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 离开教室");
                // 当执行完一个线程后，将计数器减一
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        // 如果计数器没有减到0，就将main线程阻塞，等待所有线程完成
        countDownLatch.await();
        // 同学们都离开教室后，老师main就关门
        System.out.println(Thread.currentThread().getName() + "\t 老师关门了");

        /**
         * 运行结果
         * 1	 离开教室
         * 2	 离开教室
         * 3	 离开教室
         * 4	 离开教室
         * 5	 离开教室
         * main	 老师关门了
         */
    }

}


package com.example.demo.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description: CyclicBarrier示例, 当线程数到达设定的数目后，才继续向后执行
 * @author: leitao
 * @create: 2020-01-07 11:10
 */
public class CyclicBarrierDemo {

//    public static void main(String[] args) {
//        // 以集齐七颗龙珠为例
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {
//            System.out.println(Thread.currentThread().getName()+"龙珠集齐");
//        });
//
//        for (int i = 0; i < 7; i++) {
//            new Thread(() -> {
//                // 执行该线程的业务
//                System.out.println(Thread.currentThread().getName() + "龙珠就位");
//                try {
//                    // 该线程的业务执行完后，让该线程进入等待状态，等待7龙珠集齐
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//            },String.valueOf(i)).start();
//        }
//
//    }
}

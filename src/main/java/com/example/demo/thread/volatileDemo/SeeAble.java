package com.example.demo.thread.volatileDemo;

import java.util.concurrent.TimeUnit;

/**
 * @description: volatile可见性代码示例
 * @author: leitao
 * @create: 2020-02-05 14:16
 */
public class SeeAble {
    public static void main (String[] args) {
        MyNum myNum = new MyNum();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "进入");
//            try {
//                System.out.println("开始");
//                TimeUnit.MILLISECONDS.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myNum.addShareNum();
        },"").start();

        while (myNum.shareNum == 0) {
            System.out.println("等待加1操作完成");
        }
        System.out.println(Thread.currentThread().getName() + " shareNum is " + myNum.shareNum);
    }
}

class MyNum {
    int shareNum = 0;

    public void addShareNum() {
        shareNum++;
    }
}


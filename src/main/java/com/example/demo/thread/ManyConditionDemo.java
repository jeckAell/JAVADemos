package com.example.demo.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 多条件的Condition。 Reentranlock可以精确唤醒某个线程
 *
 * 示例需求：
 *      三个线程A B C ，按照顺序执行 A -> B -> C 。
 *      A线程打印AA，B线程打印BB，C线程打印CC，循环执行10次
 * @author: leitao
 * @create: 2020-02-05 10:26
 */
public class ManyConditionDemo {
    public static void main(String[] args) {
        ShareDate shareDate = new ShareDate();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                shareDate.printA();
            }
        },"A").start();
        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                shareDate.printB();
            }
        },"B").start();
        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                shareDate.printC();
            }
        },"C").start();
    }
}

class ShareDate {
    private String nowThread = "A"; // 默认当前线程为A

    private Lock lock = new ReentrantLock();
    // 定义三个锁条件
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void printA() {
        lock.lock();
        try {
            // 判断
            while(!nowThread.equals("A")) {
                conditionA.await();
            }

            // 干活
            System.out.println(" AA");

            // 通知
            nowThread = "B";
            conditionB.signal(); // 唤醒B

        }catch (Exception e){
            System.out.println("抛出异常");
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            // 判断
            while(!nowThread.equals("B")) {
                conditionB.await();
            }

            // 干活
            System.out.println(" BB");

            // 通知
            nowThread = "C";
            conditionC.signal(); // 唤醒C

        }catch (Exception e){
            System.out.println("抛出异常");
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            // 判断
            while(!nowThread.equals("C")) {
                conditionC.await();
            }

            // 干活
            System.out.println(" CC");

            // 通知
            nowThread = "A";
            conditionA.signal(); // 唤醒A

        }catch (Exception e){
            System.out.println("抛出异常");
        } finally {
            lock.unlock();
        }
    }

}
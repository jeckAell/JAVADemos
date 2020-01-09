package com.example.demo.thread;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @description: ABA问题
 * @author: leitao
 * @create: 2020-01-09 09:10
 */
public class ABAProblem {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("================ABA问题的出现===================");
        AtomicReference<Integer> atomicReference = new AtomicReference<>(20);
        new Thread(() -> {
            // 修改原子类型的值,compareAndSet(v1,v2); 如果当前值是V1,则修改当前值为V2
            System.out.println("操作了一次");
            atomicReference.compareAndSet(20,21);
            System.out.println("操作了一次");
            atomicReference.compareAndSet(21,22);
            System.out.println("操作了一次");
            atomicReference.compareAndSet(22,20);  // 最后将值改回最初的值
        },"T1").start();

        new Thread(() -> {
            // 等待上面的线程完成操作
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 这句代码最后会被执行成功，但这句话的本意却是想在变量没有被修改的时候再执行，而在这之前该变量已经进行了修改。这就是ABA问题
            atomicReference.compareAndSet(20,200);
            System.out.println(Thread.currentThread().getName() + " 当前值为：" + atomicReference.get());
        },"T2").start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("===================ABA问题的解决=================");

        // 使用加入版本号的原子引用类解决。  AtomicStampedReference(v1,v2) v1是初始值，v2是初始的版本号
        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(20,1);

        new Thread(() -> {
            int stampe = stampedReference.getStamp();  // 获取当前版本号
            System.out.println("操作了一次");
            // compareAndSet(v1.v2,v3,v4); v1 v2 和上面的一样，V3是期望版本号，V4是修改后的版本号
            stampedReference.compareAndSet(20,21,stampe, stampe + 1);
            System.out.println("操作了一次");
            stampedReference.compareAndSet(21,22,stampedReference.getStamp(), stampedReference.getStamp() + 1);
            System.out.println("操作了一次");
            stampedReference.compareAndSet(22,20, stampedReference.getStamp(), stampedReference.getStamp() + 1);  // 最后将值改回最初的值
        },"T3").start();

        new Thread(() -> {
            // 等待上面的线程完成操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int stampe = stampedReference.getStamp();  // 获取当前版本号
            // 这句代码最后会被执行成功，但这句话的本意却是想在变量没有被修改的时候再执行，而在这之前该变量已经进行了修改。这就是ABA问题
            stampedReference.compareAndSet(20,200,1, stampe + 1);
            System.out.println(stampedReference.getStamp() + " 当前值为：" + stampedReference.getReference());
        },"T4").start();


        System.out.println("====================原子类封装对像===================");
        UserDemo userDemo = new UserDemo();
        userDemo.setAge(12);
        userDemo.setName("leitao");

        AtomicReference<UserDemo> userReference = new AtomicReference<>(userDemo);

        UserDemo userDemo1 = new UserDemo();
        userDemo1.setName("xiaowang");
        userDemo1.setAge(20);
        userReference.compareAndSet(userDemo,userDemo1);
        UserDemo u = userReference.get();
        System.out.println("now user name is "+ userReference.get().getName());
        System.out.println("now user age is "+ userReference.get().getAge());
    }
}

@Getter
@Setter
class UserDemo {
    private String name;
    private int age;
}

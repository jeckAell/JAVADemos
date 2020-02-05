package com.example.demo.thread.singletonDemo;

/**
 * @description: 传统的单线程下的单例模式
 * @author: leitao
 * @create: 2020-02-05 14:56
 */
public class TransSingletonDemo {
    private static TransSingletonDemo instance = null;
    private TransSingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "我是构造方法");
    }
    public static TransSingletonDemo getInstance() {
        if (instance == null) {
            instance = new TransSingletonDemo();
        }
        return instance;
    }
    public static void main(String[] args) {
// 单线程下的单例模式，没问题
        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
    }
}

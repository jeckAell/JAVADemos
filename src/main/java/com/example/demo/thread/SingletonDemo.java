package com.example.demo.thread;

/**
 * @description: 多线程下的单例模式
 * @author: leitao
 * @create: 2020-01-03 14:21
 */
public class SingletonDemo {

    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "我是构造方法");
    }

    public static SingletonDemo getInstance() {
        // 使用双重检测锁, 由于存在指令重排，所以给单例对象变量加volatile修饰
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }

        return instance;
    }

    public static void main(String[] args) {
        // 多线程下的单例模式
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}

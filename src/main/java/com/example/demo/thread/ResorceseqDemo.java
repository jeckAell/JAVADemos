package com.example.demo.thread;

/**
 * @description: 指令重排示例
 * @author: leitao
 * @create: 2020-01-03 13:58
 */
public class ResorceseqDemo {
    int a = 0;
    boolean flag = false;

    public void function1() {
        a = 1;
        flag = true;
    }

    public void function2() {
        if (false) {
            a = a + 5;
            System.out.println("get value is " + a);
        }
    }
}

package com.example.demo.thread.volatileDemo;

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

/**
 * 在多线程中，如果线程A执行的是 function1，线程B执行的是function2.
 * 由于指令重排的存在，当A执行function1时，语句1和语句2的执行顺序可能会重排，变成先执行语句1再执行语句2.
 * 所以如果当A先执行完语句1后，flag变为true，程B中的function2立马开始执行了，if判断通过，此时的a还是0，最后得到的结果就是 a = 0 + 5 , 显然结果不是我们预期的。
 * ===================解决======================
 * 可以使用synchorized，或者volatile
 */

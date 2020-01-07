package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private static ThreadLocal<Integer> t1 = new ThreadLocal<Integer>();
    private static int a = 0;

    @Test
    public void contextLoads() {
    }

    /**
     * 当一个线程访问某对象的synchronized方法或者synchronized代码块时，其他线程对该对象的该synchronized方法或者synchronized代码块的访问将被阻塞。
     */
    @Test
    public void testThread1() {
        class MyThread extends Thread {
            private int a = 5;

            public void run() {
                synchronized (this) {
                    for (int i = 1; i <= 5; i++) {
                        a += 1;
                        System.out.println(Thread.currentThread().getName() + " " + i);
                    }
                }
            }
        }

        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        t1.start();
        t2.start();
        t3.start();
    }


    @Test
    public void testThread2() {
        class ThreadLocalThrad extends Thread {
            public ThreadLocalThrad(String name) {
                super(name);
            }

            public void run() {
                try {
                    for (int i = 0; i < 3; i++) {
                        t1.set(a + 1);
//                        a = a+1;
                        System.out.println(this.getName() + " get values--> " + t1.get());
//                        System.out.println(this.getName() + " get values--> " +a);
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        ThreadLocalThrad one = new ThreadLocalThrad("one");
        ThreadLocalThrad two = new ThreadLocalThrad("two");
        ThreadLocalThrad three = new ThreadLocalThrad("three");

        one.start();
        two.start();
        three.start();

    }
}

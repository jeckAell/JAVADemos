package com.example.demo.thread;

import ch.qos.logback.core.util.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description: Arraylist集合中的线程不安全
 * @author: leitao
 * @create: 2020-01-09 14:21
 */
public class ArraylistThreads {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("==================================Arraylist集合线程不安全的示例===================================");
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                arrayList.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(arrayList);
            },String.valueOf(i)).start();
        }

        /**
         * 运行上面代码返回的结果为：
         * [null, 5ffad665]
         * [null, 5ffad665, 2784ac9d]
         * [null, 5ffad665, 2784ac9d, 17a9381c, ade5d8fa, 4a0f9d5c]
         * [null, 5ffad665, 2784ac9d, 17a9381c, ade5d8fa, 4a0f9d5c, 0cdeacef]
         * [null, 5ffad665, 2784ac9d, 17a9381c, ade5d8fa, 4a0f9d5c, 0cdeacef, 42470925, 39cc8ac4]
         * Exception in thread "9" Exception in thread "6" [null, 5ffad665, 2784ac9d, 17a9381c, ade5d8fa, 4a0f9d5c, 0cdeacef, 42470925, 39cc8ac4, 24c9c1f3, 129aa3c2]
         * java.util.ConcurrentModificationException
         * 	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:909)
         * 	at java.util.ArrayList$Itr.next(ArrayList.java:859)
         * 	at java.util.AbstractCollection.toString(AbstractCollection.java:461)
         *
         * 	============================分析===================================
         * 	1. 错误现象：
         * 	    首先注意报错信息 ： ConcurrentModificationException
         * 	    意思是：并发修改异常。
         *  2.导致原因：
         * 	    当多个线程并发同时往一个arraylist中添加元素时，当两个线程同时在添加的时候，可能添加到了同一个位置时，无法确定list中存两个线程中的哪一个
         *
         * 	3.解决方案
         *      3.1 使用new Vector()，但是Vector的并发性并不高，所以不考虑使用这个
         *      3.2 Collections.synchronizedList(new ArrayList<>());
         *      3.3 CopyOnWriteArrayList() 写时复制
         * 	4.优化建议
         *
         */


        Thread.sleep(2000);
        System.out.println("====================使用Collections.synchronizedList解决问题===================");
        // 构造一个线程安全的list
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }


        Thread.sleep(2000);
        System.out.println("====================使用CopyOnWriteArrayList()解决问题===================");
        // 构造一个线程安全的list
        List<String> list2 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list2.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list2);
            },String.valueOf(i)).start();
        }
    }
}

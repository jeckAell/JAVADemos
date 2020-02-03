package com.example.demo.thread;

import ch.qos.logback.core.util.TimeUtil;

import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description: 阻塞队列Demo
 *      BlockingQueue 继承自Collection
 * @author: leitao
 * @create: 2020-02-03 13:53
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        // 定义一个初始值为3的队列
        BlockingQueue<String> myQueue = new ArrayBlockingQueue<>(3);

        System.out.println("=====================使用add(), remove()================");
        // 使用add给队列添加元素, add() 方法添加成功返回true，失败则会抛出异常
        System.out.println("使用add添加元素:"+myQueue.add("a"));
        System.out.println("使用add添加元素:"+myQueue.add("b"));
        System.out.println("使用add添加元素:"+myQueue.add("c"));

        System.out.println("使用element查看第一个元素"+myQueue.element());

        try {
            myQueue.add("d");
        } catch (Exception e){
            System.out.println("队列已满，add()方法抛出异常");
        }

        // 使用remove移除队列中的元素，不传参数默认移除最开始添加的元素,移除成功返回true，失败返回false
        System.out.println("使用remove移除："+myQueue.remove());
        System.out.println("使用remove移除："+myQueue.remove("l"));
        System.out.println("使用remove移除："+myQueue.remove("b"));

        System.out.println("使用remove移除："+myQueue.remove("c"));

        try {
            System.out.println(myQueue.element());
        }catch (Exception e){
           System.out.println("队列中的元素已经移除完毕，当队列中没有任何元素时，element()方法将抛出异常");
        }

        try {
            myQueue.remove();
        }catch (Exception e){
            System.out.println("当队列为空时，remove方法也会抛出异常");
        }

        System.out.println("=====================使用offer(), poll()================");
        // offer()方法添加成功返回true，添加失败返回false
        System.out.println("使用offer添加:"+myQueue.offer("11"));
        System.out.println("使用offer添加:"+myQueue.offer("22"));
        System.out.println("使用offer添加:"+myQueue.offer("33"));
        System.out.println("使用offer添加:"+myQueue.offer("44"));
        // 使用peek返回当前第一个元素，空队列返回null
        System.out.println("当前第一个元素:" + myQueue.peek());

        // poll()方法移除元素，成功失败返回布尔值。空队列返回null
        System.out.println("poll 移除第一个元素:"+myQueue.poll());
        System.out.println("当前第一个元素:" + myQueue.peek());
        System.out.println("poll 移除元素:"+myQueue.poll());
        System.out.println("poll 移除元素:"+myQueue.poll());
        System.out.println("poll 移除元素:"+myQueue.poll());
        System.out.println("当前第一个元素:" + myQueue.peek());

        System.out.println("=====================使用put(), take()================");
        // 使用put添加时，当队列满了之后，线程会进入阻塞状态，等到队列中有位置后才会添加进去，同理take也是一样

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("==========3秒后从队列中取出一个元素");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("使用take取出元素："+myQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            System.out.println("5秒后添加一个元素，为验证后面take方法阻塞情况");
            try {
                TimeUnit.SECONDS.sleep(5);
                myQueue.put("ff");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            myQueue.put("aa");
            myQueue.put("bb");
            myQueue.put("cc");
            System.out.println("=================队列添加满了，当前线程(main线程)将进入阻塞状态,等待队列中的元素腾出位置");
            myQueue.put("dd");

            // 开始取出元素
            System.out.println("使用take取出元素："+myQueue.take());
            System.out.println("使用take取出元素："+myQueue.take());
            System.out.println("使用take取出元素："+myQueue.take());
            System.out.println("队列为空,无元素可取，当前线程进入阻塞状态");
            System.out.println("使用take取出元素："+myQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=====================使用offer(e,time,unit), poll(time,unit)================");
        // 这两个方法增加了一个等待时间，也就是线程进入阻塞后的阻塞时间。

    }
}

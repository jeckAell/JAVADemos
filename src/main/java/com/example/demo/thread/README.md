##  多线程代码示例

### 一、对volatile的理解
#### 1.volatile是java虚拟机提供的轻量级的同步机制,它有三大特性

##### 1） 保证可见性
> 什么是可见性，就是在多个线程同时访问同一个资源时，当某个线程修改了主内存中该资源的值时，其他线程能够立即知道该资源发生了改变 

[代码示例](https://github.com/jeckAell/JAVADemos/blob/master/src/main/java/com/example/demo/thread/volatileDemo/SeeAble.java)

##### 2) 不保证原子性
> 什么是原子性：不可分割，完整性，一个线程在执行某个业务时，中途不能被加塞或分割。之所以不保证原子性，是因为当某个线程在操作某个内存中的资源的过程中，如果有另一个线程加入，而且将内存中的该资源进行了其他操作，从而导致原线程中的最终操作结果有错误

> **解决方式:** 加sync同步锁;  使用JUC下的 AtomicInteger 

[代码示例](https://github.com/jeckAell/JAVADemos/blob/master/src/main/java/com/example/demo/thread/volatileDemo/AtomicIntegerDemo.java)

##### 3) 有序性（禁止指令重排）
```aidl
计算机在执行程序时，为提高性能，编译器和处理器会对指令做重排，分为一下3种： 
1.编译器的重排
2.指令并行的重排
3.内存系统的重排
所以在多线程中使用变量时，不能够保证一致性，运行结果无法预测

解决：可以使用synchorized，或者volatile
```
[代码示例](https://github.com/jeckAell/JAVADemos/blob/master/src/main/java/com/example/demo/thread/volatileDemo/ResorceseqDemo.java)

#### 2.关于JMM
JMM: java内存模型
硬件中数据读取速度: cpu>内存>硬盘
由于cpu计算速度太快，内存空间可能不足，所以在cpu和内存之间有一个缓存区

#### 3.在哪些地方使用过volatile
##### 1）单例模式
- 1. [单线程下的单例模式]()
  2. [多线程下的单例模式]()
 
> DCL (double check lock)双端检索机制（双重检测锁），就是在加锁的前后，都检查一次。
  但是双重检测锁不一定能保障线程安全，因为指令重排序的存在，需要配合volatile一起使用
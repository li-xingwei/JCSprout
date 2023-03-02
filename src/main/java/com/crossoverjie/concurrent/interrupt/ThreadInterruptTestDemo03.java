package com.crossoverjie.concurrent.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

import static com.crossoverjie.concurrent.interrupt.ThreadInterruptTestDemo03.flag01;

/**
 * @author lixingwei
 * @date 2023/3/2 14:42
 */
public class ThreadInterruptTestDemo03 {
    private static final Logger log = LoggerFactory.getLogger(ThreadInterruptTestDemo03.class);
    public static volatile boolean flag01 = true;
    public static void main(String[] args) throws InterruptedException {
        Thread t = new ThreadDemo();
        t.start();
        t.interrupt();
        Thread.sleep(2000);
        log.info("主线程睡眠结束！");
        flag01 = false;
        // 主线程休眠3秒
        LockSupport.parkNanos(3 * 1000 * 1000 *1000L);
        log.info("此时线程状态：{}", t.getState());
        log.info("结束线程等待状态");
        // LockSupport.unpark(t);
        LockSupport.unpark(t);
    }
}
class ThreadDemo extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ThreadDemo.class);
    @Override
    public void run() {
        while (flag01) {
        }
        try {
            Thread.sleep(10000);
            log.info("正常睡眠结束！");
        } catch (InterruptedException e) {
            log.error("线程被中断", e);
        }
        log.info("线程中断状态：{}", Thread.currentThread().isInterrupted());
        LockSupport.park();
        log.info("测试睡眠中断后park()未成功，线程可以继续执行，此时中断状态：{}", Thread.currentThread().isInterrupted());
        log.info("再次测试是否可以park()");
        LockSupport.park();
        log.info("线程运行结束！");
    }
    // try {
    //     Thread.sleep(10000);
    //     System.out.println("又睡醒了！");
    // } catch (InterruptedException e) {
    //     e.printStackTrace();
    // }
}
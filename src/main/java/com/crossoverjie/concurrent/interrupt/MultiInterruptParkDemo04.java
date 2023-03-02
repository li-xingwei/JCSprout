package com.crossoverjie.concurrent.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author lixingwei
 * @date 2023/3/2 16:45
 * @link {@link <a href="https://cgiirw.github.io/2018/05/27/Interrupt_Ques/"/>}
 */
public class MultiInterruptParkDemo04 {

    public static volatile boolean flag = true;

    /**
     * 程序在输出了第二条打印语句后挂起，中断被sleep()抛出的中断异常处理且重置中断标记后，第一次park()不会被阻塞
     * (因为之前响应中断的是sleep()而不是park())
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t = new ThreadDemo();
        t.start();
        t.interrupt();
        flag = false;
    }

    public static class ThreadDemo extends Thread {
        private static final Logger log = LoggerFactory.getLogger(ThreadDemo.class);

        @Override
        public void run() {
            while (flag) {

            }

            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                log.error("捕捉到中断异常", e);
            }
            log.info("本打印出现在sleep()之后，并重置中断标记：{}", Thread.interrupted());
            LockSupport.park();
            log.info("本打印出现在第一个park()之后，再次重置中断标记：{}", Thread.interrupted());
            LockSupport.park();
            log.info("本打印出现在第二个park()之后");
        }
    }
}

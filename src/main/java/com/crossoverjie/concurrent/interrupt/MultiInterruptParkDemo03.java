package com.crossoverjie.concurrent.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lixingwei
 * @date 2023/3/2 16:45
 * @link {@link <a href="https://cgiirw.github.io/2018/05/27/Interrupt_Ques/"/>}
 */
public class MultiInterruptParkDemo03 {

    public static volatile boolean flag = true;

    /**
     * 程序在输出了第一条打印语句后挂起，park()虽然响应中断未被挂起且不清除中断标记，但Thread.interrupted()重置中断标记后
     * 第二次park()被挂起
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

            LockSupport.park();
            log.info("本打印出现在第一个park()之后，并重置中断标记：{}", Thread.interrupted());
            LockSupport.park();
            log.info("本打印出现在第二个park()之后，再次重置中断标记：{}", Thread.interrupted());
            LockSupport.park();
            log.info("本打印出现在第三个park()之后");
        }
    }
}

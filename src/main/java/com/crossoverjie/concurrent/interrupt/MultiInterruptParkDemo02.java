package com.crossoverjie.concurrent.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lixingwei
 * @date 2023/3/2 16:45
 * @link {@link <a href="https://cgiirw.github.io/2018/05/27/Interrupt_Ques/"/>}
 */
public class MultiInterruptParkDemo02 {

    public static volatile boolean flag = true;

    /**
     * 程序在输出了第一条打印语句后挂起，说明无论调用多少次LockSupport.unpark(t)，只会提供给线程一个许可
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t = new ThreadDemo();
        t.start();
        LockSupport.unpark(t);
        LockSupport.unpark(t);
        LockSupport.unpark(t);
        LockSupport.unpark(t);
        flag = false;
    }

    public static class ThreadDemo extends Thread {
        private static final Logger log = LoggerFactory.getLogger(ThreadDemo.class);

        @Override
        public void run() {
            while (flag) {

            }

            LockSupport.park();
            log.info("本打印出现在第一个park()之后");
            LockSupport.park();
            log.info("本打印出现在第二个park()之后");
            LockSupport.park();
            log.info("本打印出现在第三个park()之后");
        }
    }
}

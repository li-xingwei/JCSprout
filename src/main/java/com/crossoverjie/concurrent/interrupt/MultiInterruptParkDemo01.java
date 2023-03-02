package com.crossoverjie.concurrent.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lixingwei
 * @date 2023/3/2 16:35
 * @link {@link <a href="https://cgiirw.github.io/2018/05/27/Interrupt_Ques/"/>}
 */
public class MultiInterruptParkDemo01 {
    public static volatile boolean flag = true;

    /**
     * 一次中断操作后无论线程调用多少次LockSupport.park()，程序都不会挂起，而是正常运行结束
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
        private static final Logger inner = LoggerFactory.getLogger(ThreadDemo.class);

        @Override
        public void run() {
            while (flag) {

            }

            LockSupport.park();
            inner.info("本打印出现在第一个park()之后");
            LockSupport.park();
            inner.info("本打印出现在第二个park()之后");
            LockSupport.park();
            inner.info("本打印出现在第三个park()之后");
        }
    }
}

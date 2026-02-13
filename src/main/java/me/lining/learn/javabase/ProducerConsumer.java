package me.lining.learn.javabase;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author lining
 * @date 2026/02/13 22:23
 */
public class ProducerConsumer {

    static Queue<Integer> queue = new LinkedList<>();
    static Integer MAX = 100;
    static Object lock = new Object();

    public static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
        new Producer().start();
        new Consumer().start();
    }

    static class Producer extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (queue.size() == MAX) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    queue.offer(0);
                    System.out.println("Producer入队元素, 队列元素个数" + queue.size());
                    lock.notifyAll();

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    static class Consumer extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (queue.size() == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    int res = queue.poll();
                    System.out.println("Consumer出队元素, 队列元素个数" + queue.size());
                    lock.notifyAll();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

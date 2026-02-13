package me.lining.learn.javabase;

/**
 * @author lining
 * @date 2026/02/13 21:57
 */
public class TwoThreadPrintAB {

    static int i = 1;

    public static void main(String[] args) {
        notifyThreaPrint();
    }

    public static void notifyThreaPrint() {
        Object lock = new Object();
        int max = 100;
        Thread a = new Thread(() -> {
            synchronized (lock) {
                while (i < max) {
                    while (i % 3 != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("线程A打印 " + i++);
                    lock.notifyAll();
                }
            }
        });

        Thread b = new Thread(() -> {
            synchronized (lock) {
                while (i < max) {
                    while (i % 3 != 2) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("线程B打印 " + i++);
                    lock.notifyAll();
                }
            }
        });

        Thread c = new Thread(() -> {
            synchronized (lock) {
                while (i < max) {
                    while (i % 3 != 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("线程C打印 " + i++);
                    lock.notifyAll();
                }
            }
        });
        a.start();
        b.start();
        c.start();
    }
}

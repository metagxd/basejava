package com.basejava.webapp;

public class MainDeadLock {
    public static void main(String[] args) {
        final String s1 = "S1";
        final String s2 = "S2";

        Thread thread0 = new Thread(() -> {
            synchronized (s1) {
                System.out.println(s1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (s2) {
                    System.out.println(s2);
                }
            }
        });

        Thread thread1 = new Thread(() -> {
            synchronized (s2) {
                System.out.println(s2);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (s1) {
                    System.out.println(s1);
                }
            }
        });

        thread0.start();
        thread1.start();
    }
}

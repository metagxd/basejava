package com.basejava.webapp;

public class MainThread {
    int count = 0;

    public static void main(String[] args) throws InterruptedException {
        final MainThread mainThread = new MainThread();
        Runnable r = () -> {
            for (int i = 0; i < 1000; i++) {
                mainThread.inc();
            }
        };
        for (int i = 0; i < 1000; i++) {
            new Thread(r).start();
        }
        Thread.sleep(1000);
        System.out.println(mainThread.count);


    }

    private synchronized void inc() {
        count++;
    }

}

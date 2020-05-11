package com.basejava.webapp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainConcurrency {
    public static final Object LOCK = new Object();
    public static final int THREADS = 10000;
    private final AtomicInteger atomicCounter = new AtomicInteger(0);
    private int counter;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
                System.out.println(getState());
            }
        };
        thread0.start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        }).start();
        System.out.println(thread0.getState());
        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);

        //List<Thread> threads = new ArrayList<>(THREADS);
        for (int i = 0; i < THREADS; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    mainConcurrency.atomicInc();
                }
            });
          /*  Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }*/
            latch.countDown();
//            });
//            thread.start();
            //threads.add(thread);
            //thread.join();
        }
        latch.await();
        //latch.await();
        /*threads.forEach((thread1 -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));*/
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("COUNTER: " + mainConcurrency.counter);
        System.out.println("ATOMIC_COUNTER: " + mainConcurrency.atomicCounter);
    }

    private void inc() {
        counter++;
        /*synchronized (this) {
            counter++;
        }*/
    }

    private void atomicInc() {
        atomicCounter.incrementAndGet();
    }
}

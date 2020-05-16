package com.basejava.webapp;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;
    int i;
    double sin = Math.sin(30.);

    private LazySingleton() {
    }

    //double check lock
    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    int i = 13;
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }
}

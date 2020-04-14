package com.basejava.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File project = new File("C:\\basejava");
        new FileListener().list(project);
    }

    public static class FileListener {
        public void list(File path) {
            for (File file : path.listFiles()) {
                if (file.isDirectory()) {
                    list(file);
                    System.out.println(file.getAbsolutePath());
                } else {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }
}



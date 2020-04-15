package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File project = new File("C:\\basejava");
        fileListener(project);
    }

    public static void fileListener(File path) {
        for (File file : Objects.requireNonNull(path.listFiles())) {
            if (file.isDirectory()) {
                fileListener(file);
            }
            System.out.println(file.getName());
        }
    }
}



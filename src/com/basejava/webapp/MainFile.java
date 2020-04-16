package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File project = new File("C:\\basejava");
        fileListener(project, 0);
    }

    public static void fileListener(File path, int level) {
        for (File file : Objects.requireNonNull(path.listFiles())) {
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            if (file.isFile()) {
                System.out.println(file.getName());
            } else {
                System.out.println("["+file.getName()+"]");
                fileListener(file, level + 1);
            }
        }
    }
}



package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String folderPath = "C:\\basejava";
        try {
            folderPath = args[0];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        File directory = new File(folderPath);
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                String[] path2 = new String[1];
                path2[0] = file.getName();
                main(path2);
            }
            System.out.println(file.getAbsolutePath());
        }
    }
}



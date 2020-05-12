package com.basejava.webapp;

import java.util.Arrays;

public class MainStream {

    public static void main(String[] args) {
        int[] values = new int[]{1, 3, 1, 1, 1, 1, 1, 3, 2, 3, 1, 1, 1, 3, 3, 3, 3, 3, 2, 2, 1, 3};
        System.out.println(minValue(values));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((a, b) -> a * 10 + b)
                .orElse(0);
    }

}

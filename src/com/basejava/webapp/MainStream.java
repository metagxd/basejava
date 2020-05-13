package com.basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStream {

    public static void main(String[] args) {
        int[] values = new int[]{1, 3, 1, 1, 1, 1, 1, 3, 2, 3, 1, 1, 1, 3, 3, 3, 3, 3, 2, 2, 1, 3};
        int[] values2 = new int[]{1, 4, 8, 6, 9, 3, 0};
        System.out.println(minValue(values));
        System.out.println(oddOrEven(values2));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((a, b) -> a * 10 + b)
                .orElse(0);
    }

    static List<Integer> oddOrEven(int[] values) {
        int sumOfStream = Arrays.stream(values).sum();

        if (sumOfStream % 2 == 0) {
            return Arrays.stream(values)
                    .filter(x -> x % 2 == 0)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }
        return Arrays.stream(values)
                .filter(x -> x % 2 != 0)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}

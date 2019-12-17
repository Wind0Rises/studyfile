package com.boccfc.liu.card;

import java.util.HashSet;
import java.util.Set;

public class PrimeNumber {
    public static void main(String[] args) {
        printAllPrimeNumber(100);
    }

    public static void printAllPrimeNumber(int number) {
        Set<Integer> result = new HashSet<>();
        for (int i = 2; i <= number; i++) {
            result.add(i);
        }

        int end = (int) Math.floor(Math.sqrt(number));

        for (int i = 2; i <= end; i++) {
            int j = 2;
            int remove = -1;

            while ((remove = i * j) <= number) {
                result.remove(remove);
                j++;
            }
        }

        System.out.println(result);
    }
}

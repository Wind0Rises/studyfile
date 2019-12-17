package com.boccfc.liu.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArraysUnion {

    static int[] arrays1 = new int[]{1, 5, 3, 9, 9};
    static int[] arrays2 = new int[]{3, 5, 3, 9, 10, 34, 9};

    public static void main(String[] args) {
        HashMap<Integer, Integer> count = new HashMap<>();
        List<Integer> result = new ArrayList<>();

        for (int item : arrays1) {
            count.put(item, count.getOrDefault(item, 0) + 1);
        }

        for (int item : arrays2) {
            if (count.containsKey(item)) {
                count.put(item, count.get(item) - 1);

                if (!(count.get(item) > 0)) {
                    count.remove(item);
                }
                result.add(item);
            }
        }

        System.out.println(result);
    }
}

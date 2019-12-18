package com.boccfc.liu.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Liuweian
 * @version 1.0.0
 * @desc
 * @createTime 2019/12/18 18:08
 */
public class AllCollections {
    static Integer[] arrays1 = new Integer[]{1, 5, 3};

    static List<List<Integer>> result = new ArrayList<>();

    public static void main(String[] args) {
        allCollection();
    }

    public static void allCollection() {
        List<Integer> processor = Arrays.asList(arrays1);
        all(arrays1, new ArrayList<Integer>(), 0);
    }

    public static void all(Integer[] arrays, List<Integer> residue, int j) {
        System.out.println(residue);
        for (int i = j; i < arrays.length; i++) {
            residue.add(arrays[i]);
            all(arrays, residue, i + 1);
            residue.remove(arrays[i]);
        }
    }


}

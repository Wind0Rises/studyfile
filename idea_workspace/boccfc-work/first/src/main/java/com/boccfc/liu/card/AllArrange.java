package com.boccfc.liu.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2019/12/20 18:04
 */
public class AllArrange {

    static Integer[] arrays = new Integer[]{5, 3, 9};

    public static void main(String[] args) {
        List<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(4);
        test.add(6);
        all(test, new ArrayList<>());
    }

    public static void all(List<Integer> test, List<Integer> residue) {

        if (test.size() == 0) {
            System.out.println(residue);
        }

        for (int i = 0; i < test.size(); i++) {
            Integer getResult = test.get(i);
            residue.add(getResult);
            List<Integer> liu = new ArrayList<>();
            liu.addAll(test);
            liu.remove(getResult);
            all(liu, residue);
            residue.remove(getResult);
        }
    }

}

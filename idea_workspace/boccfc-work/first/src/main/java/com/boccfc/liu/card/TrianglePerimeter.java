package com.boccfc.liu.card;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class TrianglePerimeter {
    static int[] arrays = new int[]{3, 5, 3, 9};

    public static void main(String[] args) {

        int result = -1;

        Arrays.sort(arrays);

        for (int i = arrays.length -3; i >= 0; i--) {
            int a = arrays[i];
            int b = arrays[i + 1];
            int c = arrays[i + 2];
            if ((a + b) > c) {
                result = a + b + c;
                break;
            }
        }

        if (result != -1) {
            System.out.println(result);
        }
    }
}

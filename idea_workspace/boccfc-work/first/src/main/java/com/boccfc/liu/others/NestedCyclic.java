package com.boccfc.liu.others;

public class NestedCyclic {

    public static void main(String[] args) {
        liuwiean:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 3) {
                    break liuwiean;
                }
            }
        }
    }

}

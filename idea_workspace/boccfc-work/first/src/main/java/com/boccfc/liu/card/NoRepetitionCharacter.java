package com.boccfc.liu.card;

import java.util.HashMap;

public class NoRepetitionCharacter {

    public static void main(String[] args) {
        query("ioloddlsedle");
    }

    public static void query(String str) {
        HashMap<Character, Integer> count = new HashMap<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            count.put(ch, count.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (count.get(ch) <= 1) {
                System.out.println(i + "  " + ch);
                break;
            }
        }

    }

}

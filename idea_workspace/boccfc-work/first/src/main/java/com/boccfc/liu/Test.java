package com.boccfc.liu;

/**
 * @desc 
 * @author Liuweian
 * @createTime 2019/11/9 19:35
 * @version 1.0.0
 */
public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        test.firstMethod();
    }

    public int firstMethod() {
        secondMethod(2);
        System.out.println("############");
        return 8;
    }

    public int secondMethod(int i) {
        if (i == 2) {
            return 5;
        }
        return  6;
    }
}

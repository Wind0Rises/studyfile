package com.boccfc.liu.others;

public class FatherClass{

    protected String name;

    public FatherClass() {
        System.out.println("1");
    }

    public FatherClass(String name) {
        System.out.println("2");
    }

    public static void main(String[] args) {
        FatherClass fatherClass =new FatherClass("ad");
    }
}

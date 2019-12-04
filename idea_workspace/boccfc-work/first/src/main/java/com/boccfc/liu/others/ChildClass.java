package com.boccfc.liu.others;

public class ChildClass extends FatherClass {

    public ChildClass() {
        System.out.println("3");
    }

    public ChildClass(String name) {
        System.out.println("4");
        FatherClass fatherClass = new FatherClass("asdf");
    }
}

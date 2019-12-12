package com.liu.boot.auto.boot.auto;

import java.util.ArrayList;
import java.util.List;

public class ConfigBean {

    private StudentBean studentBean;

    public ConfigBean(StudentBean studentBean) {
        System.out.println("_________________");
        this.studentBean = studentBean;
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("1");
        test.add("2");
        test.add("3");
        test.add("4");
        List<String> test1 = test.subList(1, 3);
        System.out.println(test1);
    }
}

package com.liu.boot.auto.boot.auto;

public class ConfigBean {

    private StudentBean studentBean;

    public ConfigBean(StudentBean studentBean) {
        System.out.println("_________________");
        this.studentBean = studentBean;
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }
}

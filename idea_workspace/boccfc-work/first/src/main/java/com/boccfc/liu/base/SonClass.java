package com.boccfc.liu.base;

public class SonClass extends FatherClass{
	
	public String str2;
	
	static {
		System.out.println("子类---静态---代码块！");
	}
	
	{
		System.out.println("子类---普通---代码块！");
	}
	
	public SonClass() {
		str2 = "子类---参数";
		System.out.println("子类---无参---构造函数！");
		System.out.println(this.str1);
	}
}

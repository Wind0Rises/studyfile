package com.boccfc.liu.base;

public abstract class AbstractClass {
	
	public String str;
	
	static {
		System.out.println("抽象类---静态---代码块！");
	}
	
	{
		System.out.println("抽象类---普通---代码块！");
	}
	
	public AbstractClass() {
		System.out.println("抽象类---无参---构造函数！");
		str = "抽象类-----参数";
	}
}

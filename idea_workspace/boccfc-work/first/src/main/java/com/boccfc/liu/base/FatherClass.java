package com.boccfc.liu.base;

public class FatherClass extends AbstractClass{
	
	public String str1;
	
	static {
		System.out.println("父类---静态---代码块！");
	}
	
	{
		System.out.println("父类---普通---代码块！");
	}
	
	public FatherClass() {
		str1 = "父类---参数";
		System.out.println("父类---无参---构造函数！");
		System.out.println(this.str1);
	}
}

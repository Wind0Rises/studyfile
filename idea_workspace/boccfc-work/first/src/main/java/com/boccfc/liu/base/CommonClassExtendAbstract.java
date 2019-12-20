package com.boccfc.liu.base;

public class CommonClassExtendAbstract extends ExtendsAbstractClass{
	static {
		System.out.println("普通类---静态---代码块！");
	}
	
	{
		System.out.println("普通类---普通---代码块！");
	}
	
	public CommonClassExtendAbstract() {
		System.out.println("普通类---无参---构造函数！");
	}
}

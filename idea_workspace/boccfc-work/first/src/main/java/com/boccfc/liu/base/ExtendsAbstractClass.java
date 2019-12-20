package com.boccfc.liu.base;

public abstract class ExtendsAbstractClass extends BasisClass{
	
	static {
		System.out.println("抽象类---静态---代码块！");
	}
	
	{
		System.out.println("抽象类---抽象---代码块！");
	}
	
	public ExtendsAbstractClass() {
		System.out.println("抽象类---无参---构造函数！");
	}
}

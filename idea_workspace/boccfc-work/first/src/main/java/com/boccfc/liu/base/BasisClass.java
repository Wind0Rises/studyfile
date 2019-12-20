package com.boccfc.liu.base;

public class BasisClass {
	static {
		System.out.println("基类---静态---代码块！");
	}
	
	{
		System.out.println("基类---普通---代码块！");
	}
	
	public BasisClass() {
		System.out.println("基类---无参---构造函数！");
	}
}

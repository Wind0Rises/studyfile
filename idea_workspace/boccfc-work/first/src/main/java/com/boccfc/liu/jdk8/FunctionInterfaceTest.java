package com.boccfc.liu.jdk8;

public class FunctionInterfaceTest {

    public void oneParameterTestMethod(String parameter, OneParameterInterface testInterface) {
        System.out.println("parameter1:" + parameter);
        System.out.println("testInterface:" + testInterface);

        String liu = testInterface.test(parameter);
        System.out.println("函数接口的返回：" + liu);

    }

    public void twoParameterTestMethod(String parameter, TwoParameterInterface testInterface) {
        System.out.println("parameter1:" + parameter);
        System.out.println("testInterface:" + testInterface);

        // TwoParameterInterface的test方法的两个参数，可以在方法中自己组织。也可以直接使用twoParameterTestMethod的参数。
        // 具体参数由使用FunctionInterface方法确定。
        String liu = testInterface.test(parameter, "ok");
        System.out.println("函数接口的返回：" + liu);

    }

    public static void main(String[] args) {
        FunctionInterfaceTest test = new FunctionInterfaceTest();
        test.oneParameterTestMethod("firstParameter", key -> {
            return "oneParameterResult";
        });

        // (firstParameter, secondParameter) -> 这连个参数必须指定。
        test.twoParameterTestMethod("liu", (firstParameter, secondParameter) -> {
            System.out.println("firstParameter：" + firstParameter);
            System.out.println("secondParameter：" + secondParameter);
            return "twoParameterResult";
        });
    }
}

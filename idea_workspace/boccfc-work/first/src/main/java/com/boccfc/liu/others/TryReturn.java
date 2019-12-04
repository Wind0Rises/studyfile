package com.boccfc.liu.others;

public class TryReturn {

    public static void main(String[] args) {
        TryReturn tryReturn = new TryReturn();
        /*
        System.out.println(tryReturn.test());*/
        TestObject testObject = new TestObject();
        TestObject testObject1 = tryReturn.test1(testObject);
        System.out.println(testObject1.name);
    }

    public int test() {
        int i = 1;
        try {
            return i++;
        } finally {
            System.out.println(i);
            i++;
        }
    }

    /**
     * 返回的是对象的引用。
     */
    public TestObject test1(TestObject testObject) {
        try {
            testObject.name = "刘维安";
            return testObject;
        } finally {
            testObject.name = "历史";
        }
    }

}

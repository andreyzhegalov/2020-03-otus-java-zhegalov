package hw05.aop;

public class TestClass {
    public static void main(String[] args) {
        new TestClass().testMethod(1, (short)2, "Hello", 3F, 4.0);
    }

    public void testMethod(int iArg, short sArg, String strArg, float fArg, double dArg) {
        System.out.println(iArg);
        System.out.println(sArg);
        System.out.println(strArg);
        System.out.println(fArg);
        System.out.println(dArg);
        String str = "Hello"; }
}

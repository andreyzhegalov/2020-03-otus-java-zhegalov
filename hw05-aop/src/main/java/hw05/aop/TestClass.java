package hw05.aop;

public class TestClass {
    public static void main(String[] args) {
        new TestClass().methodWithLogAnnotation(1, (short)2, "Logged correctly", 3F, 4.0);
        new TestClass().methodWithLogAnnotation();
        new TestClass().methodWithDepricatedAnnotation("No logged parametr");
        new TestClass().methodWithoutAnnotation(1);
    }

    @Log
    public void methodWithLogAnnotation(int iArg, short sArg, String strArg, float fArg, double dArg) {
    }

    @Log
    public void methodWithLogAnnotation(){
    }

    @Deprecated
    public void methodWithDepricatedAnnotation( String strArg ) {
    }

    public int methodWithoutAnnotation(int iArg){
        return iArg;
    }
}

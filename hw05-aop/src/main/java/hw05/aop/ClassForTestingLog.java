package hw05.aop;

public class ClassForTestingLog {
    public static void main(String[] args) {
        new ClassForTestingLog().methodWithLogAnnotation(1, (short) 2, "Logged correctly", 3F, 4.0);
        new ClassForTestingLog().methodWithLogAnnotation();
        new ClassForTestingLog().methodWithDepricatedAnnotation("No logged parametr");
        new ClassForTestingLog().methodWithoutAnnotation(1);
    }

    @Log
    public void methodWithLogAnnotation(int iArg, short sArg, String strArg, float fArg, double dArg) {
    }

    @Log
    public void methodWithLogAnnotation() {
    }

    @Deprecated
    public void methodWithDepricatedAnnotation(String strArg) {
    }

    public int methodWithoutAnnotation(int iArg) {
        return iArg;
    }
}

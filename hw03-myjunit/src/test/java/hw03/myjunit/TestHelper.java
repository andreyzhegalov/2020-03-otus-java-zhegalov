package hw03.myjunit;

import java.lang.reflect.Method;

public class TestHelper {
    private TestClass testClass;
    private Method successMethod;
    private Method failMethod;
    private Object istance;
    private Class<SomeClassTest> clazz;

    TestHelper() {
        clazz = SomeClassTest.class;
        testClass = new TestClass(clazz);
        try {
            successMethod = clazz.getMethod("successTest");
        } catch (Exception e) {
        }
        try {
            failMethod = clazz.getMethod("failTest");
        } catch (Exception e) {
        }
        try {
            istance = testClass.getConstructor().newInstance();
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public TestClass getTestClass() {
        return testClass;
    }

    @Test
    public Object getIstance() {
        return istance;
    }

    @Test
    public Method getSuccessMethod() {
        return successMethod;
    }

    @Test
    public Method getFailMethod() {
        return failMethod;
    }

    @Test
    public Class<SomeClassTest> getClazz() {
        return clazz;
    }
}

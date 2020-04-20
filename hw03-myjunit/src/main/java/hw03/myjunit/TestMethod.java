package hw03.myjunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMethod {
    private final Method method;
    private TestClass testClass;

    public TestMethod(Method method, TestClass testClass) {
        this.method = method;
        this.testClass = testClass;
    }

    public void invoke(Object test) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        method.invoke(test);
    }

}


package hw03.myjunit.framework.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMethod {
    private final Method method;
    private final TestClass testClass;

    public TestMethod(final Method method, final TestClass testClass) {
        this.method = method;
        this.testClass = testClass;
    }

    public void invoke(final Object test) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        method.invoke(test);
    }

    public Method getBefore() {
        return testClass.getBefore();
    }

    public Method getAfter() {
        return testClass.getAfter();
    }

    public String getName() {
        return method.getName();
    }
}

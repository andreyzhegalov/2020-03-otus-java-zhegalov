package hw03.myjunit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class TestClass {
    private Class<?> klass;

    TestClass(Class<?> klass) {
        this.klass = klass;
    }

    public List<Method> getTestMethods() {
        return getAnnotatedMethods(Test.class);
    }

    public Constructor<?> getConstructor() throws SecurityException, NoSuchMethodException {
        return klass.getConstructor();
    }

    public Method getBefore() {
        final var methodsList = getAnnotatedMethods(Before.class);
        return (methodsList.isEmpty()) ? null : methodsList.get(0);
    }

    public Method getAfter() {
        var methodsList = getAnnotatedMethods(After.class);
        return (methodsList.isEmpty()) ? null : methodsList.get(0);
    }

    private List<Method> getAnnotatedMethods(Class<? extends Annotation> annotation) {
        List<Method> result = new ArrayList<Method>();
        for (var method : klass.getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }
}

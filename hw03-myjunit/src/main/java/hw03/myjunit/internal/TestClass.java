package hw03.myjunit.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class TestClass {
    private final Class<?> klass;

    TestClass(final Class<?> klass) {
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
        return methodsList.isEmpty() ? null : methodsList.get(0);
    }

    public Method getAfter() {
        final var methodsList = getAnnotatedMethods(After.class);
        return methodsList.isEmpty() ? null : methodsList.get(0);
    }

    private List<Method> getAnnotatedMethods(final Class<? extends Annotation> annotation) {
        final List<Method> result = new ArrayList<>();
        for (final var method : klass.getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }
}

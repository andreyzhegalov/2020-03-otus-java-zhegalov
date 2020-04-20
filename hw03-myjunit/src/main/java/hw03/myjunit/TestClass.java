package hw03.myjunit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class TestClass {
    private Class<?> klass;

    TestClass(Class<?> klass ) {
        this.klass = klass;
    }

    public List<Method> getTestMethods() {
        var list = new ArrayList<Method>();
        Method testMethod;
        try{
            testMethod = klass.getMethod("someMethodTest");
            list.add(testMethod);
        }
        catch(Exception e){
        }
        return list;
    }

    public Constructor<?> getConstructor() throws SecurityException, NoSuchMethodException {
        return klass.getConstructor();
    }
}

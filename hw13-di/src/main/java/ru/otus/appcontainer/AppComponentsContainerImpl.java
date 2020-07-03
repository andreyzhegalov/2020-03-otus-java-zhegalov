package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private Class<?> initialConfigClass;
    private final Object configIstance;

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
        this.initialConfigClass = initialConfigClass;
        try {
            this.configIstance = this.initialConfigClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Config istance not created");
        }
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        final var methods = configClass.getDeclaredMethods();
        Multimap<Integer, Object> map = ArrayListMultimap.create();
        for (final var method : methods) {
            final var findedAnnotation = AppComponent.class;
            if (!method.isAnnotationPresent(findedAnnotation)) {
                continue;
            }
            final var annotation = method.getAnnotation(findedAnnotation);
            final int order = annotation.order();
            map.put(order, method);
            appComponentsByName.put(annotation.name(), method);
        }
        map.forEach((key, valueCollection) -> appComponents.add(valueCollection));
    }

    private void checkConfigClass(Class<?> configClass) {
        if (configClass == null) {
            throw new IllegalArgumentException(String.format("Given class is null"));
        }
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object[] getMethodArgs(Method method) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object object : appComponents) {
            final Method method = (Method) object;
            final var returnMethodType = method.getReturnType();
            if (returnMethodType.isAssignableFrom(componentClass)) {
                final var args = getMethodArgs(method);
                try {
                    return (C) method.invoke(configIstance, args);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        // Получение по имени идентификатора
        return null;
    }
}

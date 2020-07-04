package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... configClasses) {
        for (Class<?> config : configClasses) {
            processConfig(config);
        }
    }

    public AppComponentsContainerImpl(String packageName) {
        final ConfigurationBuilder reflectionConfig = new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner())
                .setUrls(ClasspathHelper.forPackage(packageName));
        final Reflections reflections = new Reflections(reflectionConfig);
        final Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);
        for (Class<?> config : annotated) {
            processConfig(config);
        }
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        final var methods = configClass.getDeclaredMethods();
        for (final var method : methods) {
            final var findedAnnotation = AppComponent.class;
            if (!method.isAnnotationPresent(findedAnnotation)) {
                continue;
            }
            appComponents.add(method);
            final var annotation = method.getAnnotation(findedAnnotation);
            appComponentsByName.put(annotation.name(), method);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (configClass == null) {
            throw new IllegalArgumentException(String.format("Given class is null"));
        }
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object[] getArgsObject(Class<?>[] argsTypes) {
        final List<Object> args = new ArrayList<>();
        for (Class<?> type : argsTypes) {
            args.add(getAppComponent(type));
        }
        return args.toArray();
    }

    @SuppressWarnings("unchecked")
    private <C> C getComponent(Method method) {
        final Class<?>[] argsTypes = method.getParameterTypes();
        final var args = getArgsObject(argsTypes);
        final var classWithMethod = method.getDeclaringClass();
        try {
            final var instance = classWithMethod.getDeclaredConstructor().newInstance();
            return (C) method.invoke(instance, args);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object object : appComponents) {
            final Method method = (Method) object;
            final Class<?> returnMethodType = method.getReturnType();
            if (returnMethodType.isAssignableFrom(componentClass)) {
                return getComponent(method);
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        final Method method = (Method) appComponentsByName.get(componentName);
        return (method == null) ? null : getComponent(method);
    }
}

package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

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
        processAllConfig(configClasses);
    }

    public AppComponentsContainerImpl(String packageName) {
        final ConfigurationBuilder reflectionConfig = new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner())
                .setUrls(ClasspathHelper.forPackage(packageName));
        final Reflections reflections = new Reflections(reflectionConfig);
        final Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);
        processAllConfig(annotated.toArray(new Class<?>[0]));
    }

    private void processAllConfig(Class<?>[] allConfig) {
        final List<Class<?>> sortedConfig = sortConfigClasses(allConfig);
        for (Class<?> config : sortedConfig) {
            processConfig(config);
        }
    }

    private void processConfig(Class<?> configClass) {
        final var allMethods = loadAllAnnotatedMethods(configClass.getDeclaredMethods());
        for (Method method : allMethods) {
            final var component = makeComponent(method);
            if (component == null) {
                throw new RuntimeException("Component not created");
            }
            appComponents.add(component);
            final String name = method.getAnnotation(AppComponent.class).name();
            appComponentsByName.put(name, component);
        }
    }

    private List<Class<?>> sortConfigClasses(Class<?>[] configClasses) {
        final Multimap<Integer, Class<?>> map = ArrayListMultimap.create();
        for (final var configClass : configClasses) {
            checkConfigClass(configClass);
            final var findedAnnotation = AppComponentsContainerConfig.class;
            if (!configClass.isAnnotationPresent(findedAnnotation)) {
                continue;
            }
            final var annotation = configClass.getAnnotation(findedAnnotation);
            final int order = annotation.order();
            map.put(order, configClass);
        }
        final List<Class<?>> result = new ArrayList<>();
        map.forEach((key, valueCollection) -> result.add(valueCollection));
        return result;
    }

    private List<Method> loadAllAnnotatedMethods(Method[] methods) {
        final Multimap<Integer, Method> map = ArrayListMultimap.create();
        for (final var method : methods) {
            final var findedAnnotation = AppComponent.class;
            if (!method.isAnnotationPresent(findedAnnotation)) {
                continue;
            }
            final var annotation = method.getAnnotation(findedAnnotation);
            final int order = annotation.order();
            map.put(order, method);
        }
        final List<Method> result = new ArrayList<>();
        map.forEach((key, valueCollection) -> result.add(valueCollection));
        return result;
    }

    @SuppressWarnings("unchecked")
    private <C> C makeComponent(Method method) {
        final var classWithMethod = method.getDeclaringClass();
        final Class<?>[] argsTypes = method.getParameterTypes();
        final var args = getArgsObject(argsTypes);
        try {
            final var instance = classWithMethod.getDeclaredConstructor().newInstance();
            return (C) method.invoke(instance, args);
        } catch (Exception e) {
            return null;
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (configClass == null) {
            throw new IllegalArgumentException("Given class is null");
        }
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object[] getArgsObject(Class<?>[] argsTypes) {
        final List<Object> args = new ArrayList<>();
        for (Class<?> type : argsTypes) {
            final var appComponent = getAppComponent(type);
            if (appComponent == null) {
                throw new RuntimeException(String.format("No component of type %s in the context", type));
            }
            args.add(appComponent);
        }
        return args.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object object : appComponents) {
            if (componentClass.isAssignableFrom(object.getClass())) {
                return (C) object;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        final Object component = appComponentsByName.get(componentName);
        return (component == null) ? null : (C) component;
    }
}

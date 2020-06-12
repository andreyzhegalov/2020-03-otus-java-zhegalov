package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class EntityClass<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;

    public EntityClass(Class<T> entityType) {
        this.clazz = entityType;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        Constructor<?>[] ctrs = clazz.getConstructors();
        for (final Constructor<?> constructor : ctrs) {
            if (constructor.getParameterCount() == 0) {
                return (Constructor<T>) constructor;
            }
        }
        throw new MapperException("not allowded constructor founded");
    }

    @Override
    public Field getIdField() {
        final List<Field> res = new ArrayList<>();
        for (final var field : getAllFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                res.add(field);
            }
        }
        if (res.isEmpty()) {
            throw new RuntimeException("No Id annotation");
        }
        if (res.size() > 1) {
            throw new RuntimeException("Above one Id annotation present");
        }
        return res.get(0);
    }

    @Override
    public List<Field> getAllFields() {
        final List<Field> res = new ArrayList<>();
        for (final var field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isSynthetic()) {
                continue;
            }
            res.add(field);
        }
        return res;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        final var res = getAllFields();
        res.remove(getIdField());
        return res;
    }

    public void setField(T entity, String fieldName, Object value){
        try {
            final var field = getField(fieldName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }

    private Field getField(String fieldName) {
        return getAllFields().stream().filter((f) -> f.getName().equals(fieldName)).findFirst().orElseThrow();
    }
}

package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class EntityClass<T> implements EntityClassMetaData<T> {
    private final T entity;
    private final Class<T> clazz;

    public EntityClass(Class<T> entityType) {
        this.entity = null;
        this.clazz = entityType;
    }

    public T getEntity() {
        return this.entity;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        Constructor<?>[] ctrs = clazz.getConstructors();
        if (ctrs.length > 1 || ctrs.length < 1){
            throw new MapperException("Multiple constructors not supported");
        }
        return (Constructor<T>)ctrs[0];
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
}

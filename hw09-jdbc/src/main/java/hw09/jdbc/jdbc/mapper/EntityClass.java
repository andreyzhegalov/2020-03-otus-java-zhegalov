package hw09.jdbc.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class EntityClass<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final List<Field> fields;
    private final List<Field> fieldsWithoutId;
    private final Field idField;
    private final Constructor<T> constructor;

    public EntityClass(Class<T> entityType) {
        if (entityType == null) {
            throw new MapperException(new IllegalArgumentException());
        }
        this.clazz = entityType;
        this.fields = new ArrayList<>();
        for (final var field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field);
        }
        this.idField = findIdField();
        fieldsWithoutId = this.fields.stream().filter((f) -> !f.equals(this.idField)).collect(Collectors.toList());
        this.constructor = findConstructor();
    }

    private Field findIdField() {
        final List<Field> res = new ArrayList<>();
        for (final var field : getAllFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                res.add(field);
            }
        }
        if (res.isEmpty()) {
            throw new MapperException("No Id annotation in class " + clazz);
        }
        if (res.size() > 1) {
            throw new MapperException("Above one Id annotation present in class " + clazz);
        }
        return res.get(0);
    }

    @SuppressWarnings("unchecked")
    private Constructor<T> findConstructor() {
        Constructor<?>[] ctrs = clazz.getConstructors();
        for (final Constructor<?> constructor : ctrs) {
            if (constructor.getParameterCount() == 0) {
                return (Constructor<T>) constructor;
            }
        }
        throw new MapperException("Not allowded constructor founded in class " + clazz);
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return this.fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldsWithoutId;
    }

    public void setFieldValue(T entity, String fieldName, Object value) {
        try {
            final var field = getField(fieldName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }

    public Object getFieldValue(T entity, String fieldName) {
        try {
            final var field = getField(fieldName);
            return field.get(entity);
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }

    public long getIdValue(T entity) {
        return (long) getFieldValue(entity, getIdField().getName());
    }

    private Field getField(String fieldName) {
        return getAllFields().stream().filter((f) -> f.getName().equals(fieldName)).findFirst()
                .orElseThrow(() -> new MapperException("Field with name " + fieldName + " not found"));
    }
}

package hw08.mygson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class ObjectField {
    private Field field;
    private Class<?> fieldType;
    private Object obj;

    public ObjectField(Field field, Object obj) {
        this.field = field;
        this.fieldType = field.getType();
        this.obj = obj;
    }

    public String getName() {
        return field.getName();
    }

    public Object toObject() throws IllegalArgumentException, IllegalAccessException {
        if (fieldType.isPrimitive()) {
            if (fieldType.equals(boolean.class)) {
                return field.getBoolean(obj);
            }
            if (fieldType.equals(int.class)) {
                return field.getInt(obj);
            }
        }
        else if (isArray()){
            return toArrayObject();
        }
        return field.get(this.obj);
    }

    private boolean isArray() {
        final boolean isArray = fieldType.isArray();
        final boolean isCollection = Collection.class.isAssignableFrom(fieldType);
        return isArray || isCollection;
    }

    private Object[] toArrayObject() throws IllegalArgumentException, IllegalAccessException {
        final Object[] array;
        if (fieldType.isArray()) {
            if (field.get(obj) instanceof Object[]) {
                array = (Object[]) field.get(obj);
            } else {
                array = fromPrimitiveArray(field.get(obj));
            }
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            Collection<Object> collection = new ArrayList<>();
            collection = (Collection<Object>) field.get(obj);
            array = collection.toArray();
        } else {
            throw new UnsupportedOperationException();
        }
        return array;
    }

    private Object[] fromPrimitiveArray(Object obj) {
        final Object[] array = new Object[Array.getLength(obj)];
        for (int index = 0; index < Array.getLength(obj); index++) {
            array[index] = Array.get(obj, index);
        }
        return array;
    }
}

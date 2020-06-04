package hw08.mygson.object;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import hw08.mygson.MyGsonException;

public class FieldHandler {
    private Field field;
    private Object obj;

    public FieldHandler(Field field, Object obj) {
        if (field == null) {
            throw new MyGsonException("Field is null");
        }
        if (obj == null) {
            throw new MyGsonException("Obj is null");
        }
        field.setAccessible(true);
        this.field = field;
        this.obj = obj;
    }

    public boolean isSynthetic() {
        return field.isSynthetic();
    }

    public String getName() {
        return field.getName();
    }

    public Object getObject() {
        Object obj = null;
        try {
            final var typeOfInstance = field.get(this.obj).getClass();
            if (getType().isPrimitive()) {
                obj = field.get(this.obj);
            } else if (getType().equals(String.class)) {
                obj = field.get(this.obj);
            } else if (isArray(typeOfInstance)) {
                obj = toArrayObject(typeOfInstance);
            } else if (typeOfInstance.isMemberClass()) {
                obj = field.get(this.obj);
            } else {
                throw new MyGsonException("Unsupported field type");
            }
        } catch (IllegalArgumentException e) {
            throw new MyGsonException("Error get value from field " + getName());
        } catch (IllegalAccessException e) {
            throw new MyGsonException("Error get value from field " + getName());
        }
        return obj;
    }

    public Class<?> getType() {
        return field.getType();
    }

    private boolean isArray(Class<?> type) {
        final boolean isArray = type.isArray();
        final boolean isCollection = Collection.class.isAssignableFrom(type);
        return isArray || isCollection;
    }

    private Object[] toArrayObject(Class<?> type) throws IllegalArgumentException, IllegalAccessException {
        final Object[] array;
        if (type.isArray()) {
            if (field.get(obj) instanceof Object[]) {
                array = (Object[]) field.get(obj);
            } else {
                array = fromPrimitiveArray(field.get(obj));
            }
        } else if (Collection.class.isAssignableFrom(type)) {
            Collection<Object> collection = new ArrayList<>();
            collection = (Collection<Object>) field.get(obj);
            array = collection.toArray();
        } else {
            throw new MyGsonException("Internal error");
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

package hw08.mygson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class FieldHandler {
    private Field field;
    private Object obj;

    public FieldHandler(Field field, Object obj) {
        this.field = field;
        this.obj = obj;
    }

    public String getName() {
        return field.getName();
    }

    public Object getObject() {
        Object obj = null;
        try {
            if (isArray()) {
                obj = toArrayObject();
            } else {
                obj = field.get(this.obj);
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

    private boolean isArray() {
        final boolean isArray = getType().isArray();
        final boolean isCollection = Collection.class.isAssignableFrom(getType());
        return isArray || isCollection;
    }

    private Object[] toArrayObject() throws IllegalArgumentException, IllegalAccessException {
        final Object[] array;
        if (getType().isArray()) {
            if (field.get(obj) instanceof Object[]) {
                array = (Object[]) field.get(obj);
            } else {
                array = fromPrimitiveArray(field.get(obj));
            }
        } else if (Collection.class.isAssignableFrom(getType())) {
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

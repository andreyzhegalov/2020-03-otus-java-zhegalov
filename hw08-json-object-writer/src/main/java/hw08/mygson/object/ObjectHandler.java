package hw08.mygson.object;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hw08.mygson.MyGsonException;

public class ObjectHandler {
    final private Object obj;
    final private Class<?> clazz;
    final private Field[] fields;

    public ObjectHandler(Object obj) {
        this.obj = obj;
        this.clazz = this.obj.getClass();
        this.fields = clazz.getDeclaredFields();
    }

    public List<FieldHandler> getFields() {
        List<FieldHandler> res = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            final var field = fields[i];
            final var filedHandler = new FieldHandler(field, this.obj);
            if (filedHandler.isSynthetic()) {
                continue;
            }
            res.add(filedHandler);
        }
        return res;
    }

    public boolean isPrimitiveWrapper() {
        return clazz.equals(Boolean.class) || clazz.equals(Short.class) || clazz.equals(Integer.class)
                || clazz.equals(Long.class) || clazz.equals(Character.class) || clazz.equals(Byte.class)
                || clazz.equals(Float.class) || clazz.equals(Double.class);
    }

    public boolean isString() {
        return getType().equals(String.class);
    }

    public Class<?> getType() {
        return obj.getClass();
    }

    public Object getObject() {
        if (!isAllowableType()) {
            throw new MyGsonException("Unsupported field type");
        }
        if (isArray()) {
            return toArrayOfObject();
        }
        return this.obj;
    }

    public boolean isAllowableType() {
        return isString() || isArray() || getType().isMemberClass() || isPrimitiveWrapper();
    }

    public boolean isArray() {
        final boolean isArray = getType().isArray();
        final boolean isCollection = Collection.class.isAssignableFrom(getType());
        return isArray || isCollection;
    }

    private Object[] toArrayOfObject() {
        if (getType().isArray()) {
            final Object[] array = new Object[Array.getLength(obj)];
            for (int index = 0; index < array.length; index++) {
                array[index] = Array.get(obj, index);
            }
            return array;
        }
        if (Collection.class.isAssignableFrom(getType())) {
            Collection<Object> collection = new ArrayList<>();
            collection = (Collection<Object>) this.obj;
            return collection.toArray();
        }
        throw new MyGsonException("Internal error");
    }
}

package hw08.mygson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class JsonObject {
    private final Field field;
    private final Object obj;

    public JsonObject(Field field, Object obj) {
        this.field = field;
        this.obj = obj;
    }

    public String getKey() {
        return wrap(field.getName());
    }

    public String getValue() {
        String value = new String();
        final var fieldType = field.getType();

        if (fieldType.isPrimitive()) {
            if (fieldType.equals(boolean.class)) {
                try {
                    value = String.valueOf(field.getBoolean(obj));
                } catch (IllegalArgumentException e) {
                    throw new MyGsonException("Not boolean type");
                } catch (IllegalAccessException e) {
                    throw new MyGsonException("Field not accesible");
                }
            }

            if (fieldType.equals(int.class)) {
                try {
                    value = String.valueOf(field.getInt(obj));
                } catch (IllegalArgumentException e) {
                    throw new MyGsonException("Not integer type");
                } catch (IllegalAccessException e) {
                    throw new MyGsonException("Field not accesible");
                }
            }
        }

        if (fieldType.isArray()) {
            final Object[] array;
            try {
                if (field.get(obj) instanceof Object[]) {
                    array = (Object[]) field.get(obj);
                } else {
                    array = fromPrimitiveArray(field.get(obj));
                }
            } catch (IllegalArgumentException e) {
                throw new MyGsonException("Not array type");
            } catch (IllegalAccessException e) {
                throw new MyGsonException("Field not accesible");
            }
            value = arrayToString(array);
        }

        if (Collection.class.isAssignableFrom(fieldType)) {
            Collection<Object> collection = new ArrayList<>();
            try {
                collection = (Collection<Object>) field.get(obj);
            } catch (IllegalArgumentException e) {
                throw new MyGsonException("Not array type");
            } catch (IllegalAccessException e) {
                throw new MyGsonException("Field not accesible");
            }
            value = arrayToString(collection.toArray());
        }

        return value;
    }

    private String wrap(String value) {
        return "\"" + value + "\"";
    }

    private Object[] fromPrimitiveArray(Object obj) {
        final Object[] array = new Object[Array.getLength(obj)];
        for (int index = 0; index < Array.getLength(obj); index++) {
            array[index] = Array.get(obj, index);
        }
        return array;
    }

    private String arrayToString(Object[] array) {
        String value = "[";
        for (int i = 0; i < array.length; i++) {
            if (array[i].getClass().equals(String.class)) {
                value += wrap(array[i].toString());
            } else {
                value += array[i].toString();
            }

            if (i < array.length - 1) {
                value += ",";
            }
        }
        value += "]";
        return value;
    }
}

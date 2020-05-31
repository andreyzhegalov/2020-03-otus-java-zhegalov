package hw08.mygson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MyGson {

    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        final var clazz = obj.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        String res = "{";
        for (int i = 0; i < fields.length; i++) {
            final var field = fields[i];
            field.setAccessible(true);

            if (field.getName().contains("this$0")) {
                continue;
            }
            res += wrapName(field.getName()) + ":" + getValue(field, obj);
        }
        return res + "}";
    }

    private String wrapName(String value) {
        return "\"" + value + "\"";
    }

    private String getValue(Field field, Object obj) {
        String value = new String();
        final var fieldType = field.getType();

        if (fieldType.toString().equals("boolean")) {
            try {
                value = String.valueOf(field.getBoolean(obj));
            } catch (IllegalArgumentException e) {
                throw new MyGsonException("Not boolean type");
            } catch (IllegalAccessException e) {
                throw new MyGsonException("Field not accesible");
            }
        }

        if (fieldType.toString().equals("int")) {
            try {
                value = String.valueOf(field.getInt(obj));
            } catch (IllegalArgumentException e) {
                throw new MyGsonException("Not integer type");
            } catch (IllegalAccessException e) {
                throw new MyGsonException("Field not accesible");
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

        if (Iterable.class.isAssignableFrom(fieldType)) {
            List<Object> list = new ArrayList<>();
            if (List.class.isAssignableFrom(fieldType)) {
                try {
                    list = (List<Object>) field.get(obj);
                } catch (IllegalArgumentException e) {
                    throw new MyGsonException("Not array type");
                } catch (IllegalAccessException e) {
                    throw new MyGsonException("Field not accesible");
                }
            }
            value = arrayToString(list.toArray());
        }

        return value;
    }

    private Object[] fromPrimitiveArray(Object obj) {
        final Object[] array = new Object[Array.getLength(obj)];
        for (int index = 0; index < Array.getLength(obj); index++) {
            array[index] = Array.get(obj, index); // automatic boxing
        }
        return array;
    }

    private String arrayToString(Object[] array) {
        String value = "[";
        for (int i = 0; i < array.length; i++) {
            if(array[i].getClass().equals(String.class)){
                value += wrapName( array[i].toString());
            }
            else{
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

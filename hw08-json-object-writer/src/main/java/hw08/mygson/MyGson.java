package hw08.mygson;

import java.lang.reflect.Field;

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

        if (field.getType().toString().equals("boolean")) {
            try {
                value = String.valueOf(field.getBoolean(obj));
            } catch (IllegalArgumentException e) {
                throw new MyGsonException("Not boolean type");
            } catch (IllegalAccessException e) {
                throw new MyGsonException("Field not accesible");
            }
        }

        if (field.getType().toString().equals("int")) {
            try {
                value = String.valueOf(field.getInt(obj));
            } catch (IllegalArgumentException e) {
                throw new MyGsonException("Not integer type");
            } catch (IllegalAccessException e) {
                throw new MyGsonException("Field not accesible");
            }
        }

        return value;
    }
}

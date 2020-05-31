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

            if (field.getType().equals(boolean.class)) {
                String value;
                try {
                    value = String.valueOf(field.getBoolean(obj));
                } catch (IllegalArgumentException e) {
                    throw new MyGsonException("Not boolean type");
                } catch (IllegalAccessException e) {
                    throw new MyGsonException("Field not accesible");
                }

                res += wrapName(field.getName()) + ":" + value;
            }

        }
        return res + "}";
    }

    private String wrapName(String value) {
        return "\"" + value + "\"";
    }
}

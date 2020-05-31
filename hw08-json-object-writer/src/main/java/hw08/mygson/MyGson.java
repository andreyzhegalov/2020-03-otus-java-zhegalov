package hw08.mygson;

import java.lang.reflect.Field;

public class MyGson {

    public String toJson(Object obj) throws IllegalArgumentException, IllegalAccessException {
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
                res += wrapValue(field.getName()) + ":" + field.getBoolean(obj);
            }

        }
        return res + "}";
    }

    private String wrapValue(String value) {
        return "\"" + value + "\"";
    }
}

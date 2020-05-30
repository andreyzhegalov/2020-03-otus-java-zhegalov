package hw08.mygson;

import java.lang.reflect.Field;

public class MyGson {

    public String toJson(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return "null";
        }

        final var clazz = obj.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            final var field = fields[i];
            field.setAccessible(true);
            System.out.println(field.getType().getTypeName());
            System.out.println(field.getName());
            System.out.println(field.getBoolean(obj));
        }
        return new String();
    }
}

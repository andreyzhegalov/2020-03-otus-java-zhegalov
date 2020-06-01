package hw08.mygson;

import java.lang.reflect.Field;

public class MyGson {
    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        final var clazz = obj.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        try {
            return fieldsToJson(fields, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String();
    }


    private String fieldsToJson(Field[] fields, Object obj) throws IllegalArgumentException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < fields.length; i++) {
            final var field = fields[i];
            field.setAccessible(true);

            // TODO may be exist better variant
            if (field.getName().contains("this$0")) {
                continue;
            }

            final var fieldHandler = new ObjectField(field, obj);
            final var jsonObject = new JsonObject(field.getName(), fieldHandler.toObject());

            sb.append(jsonObject.toJson());
        }
        return sb.append("}").toString();
    }
}

package hw08.mygson;

import java.lang.reflect.Field;

public class MyGson {
    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        final var clazz = obj.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        return fieldsToJson(fields, obj);
    }

    private String fieldsToJson(Field[] fields, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < fields.length; i++) {
            final var field = fields[i];
            field.setAccessible(true);

            // TODO may be exist better variant
            if (field.getName().contains("this$0")) {
                continue;
            }

            sb.append(fieldToJson(field, obj));
        }
        return sb.append("}").toString();
    }

    private String fieldToJson(Field field, Object obj) {
        final var jsonObject = new JsonObject(field, obj);
        return jsonObject.getKey() + ":" + jsonObject.getValue();
    }
}

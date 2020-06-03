package hw08.mygson;

import java.lang.reflect.Field;

public class JsonObject {
    private final Object obj;

    public JsonObject(Object obj) {
        this.obj = obj;
    }

    public String toJson() {
        final var sb = new StringBuilder();
        sb.append("{");

        final var clazz = this.obj.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            final var field = fields[i];
            field.setAccessible(true);
            if (field.isSynthetic()) {
                continue;
            }

            final var fieldHandler = new FieldHandler(field, obj);

            sb.append(wrap((fieldHandler.getName())));
            sb.append(":");
            sb.append(new JsonValue(fieldHandler.getObject()).toJson());

            if (i == fields.length - 2) {
                break;
            }
            sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    private String wrap(String value) {
        return "\"" + value + "\"";
    }
}

package hw08.mygson;

import java.lang.reflect.Field;

public class JsonObject {
    private final Object obj;

    public JsonObject(Object obj) {
        this.obj = obj;
    }

    public StringBuilder toJson(StringBuilder sb) throws IllegalArgumentException, IllegalAccessException {
        sb.append("{");
        final var clazz = this.obj.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);

            // TODO may be exist better variant
            if (field.getName().contains("this$0")) {
                continue;
            }

            final var fieldHandler = new ObjectField(field, obj);
            sb.append(wrap((fieldHandler.getName())));
            sb.append(":");
            sb.append( JsonValue.toJson( fieldHandler.toObject()));
        }

        sb.append("}");
        return sb;
    }

    private String wrap(String value) {
        return "\"" + value + "\"";
    }
}

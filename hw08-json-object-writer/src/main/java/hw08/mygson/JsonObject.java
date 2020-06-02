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
        for (int i = 0; i < fields.length; i++) {
            final var field  = fields[i];
            field.setAccessible(true);

            final var fieldHandler = new ObjectField(field, obj);

            // TODO may be exist better variant
            if (field.getName().contains("this$0")) {
                continue;
            }

            sb.append(wrap((fieldHandler.getName())));
            sb.append(":");
            new JsonValue(fieldHandler.toObject()).toJson( sb );

            if(i == fields.length - 2){
                break;
            }
            sb.append(",");
        }
        sb.append("}");
        return sb;
    }

    private String wrap(String value) {
        return "\"" + value + "\"";
    }
}

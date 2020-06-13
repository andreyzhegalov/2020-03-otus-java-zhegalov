package hw08.mygson.json;

import hw08.mygson.object.ObjectHandler;

public class JsonValue {
    private final Object value;

    public JsonValue(Object value) {
        this.value = value;
    }

    public String toJson() {
        if (value == null) {
            return "null";
        }
        final var sb = new StringBuilder();
        final var objectHandler = new ObjectHandler(value);

        if (objectHandler.isString()) {
            sb.append(wrap(value.toString()));
        } else if (objectHandler.isPrimitiveWrapper()) {
            sb.append(value.toString());
        } else if (objectHandler.isArray()) {
            final var array = (Object[]) value;
            sb.append(new JsonArray(array).toJson());
        } else {
            sb.append(new JsonObject(value).toJson());
        }
        return sb.toString();
    }

    private static String wrap(String value) {
        return "\"" + value + "\"";
    }
}

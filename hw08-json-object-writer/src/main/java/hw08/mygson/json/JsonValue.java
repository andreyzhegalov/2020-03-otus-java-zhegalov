package hw08.mygson.json;

public class JsonValue {
    private final Object value;

    public JsonValue(Object value) {
        this.value = value;
    }

    public String toJson() {
        if (value == null){
            return "null";
        }
        final var sb = new StringBuilder();
        final var valueClass = value.getClass();
        if (valueClass.isMemberClass()) {
            sb.append(new JsonObject(value).toJson());
        } else if (valueClass.isArray()) {
            sb.append(new JsonArray((Object[]) value).toJson());
        } else if (valueClass.equals(String.class) || valueClass.equals(Character.class)) {
            sb.append(wrap(value.toString()));
        } else {
            sb.append(value.toString());
        }
        return sb.toString();
    }

    private static String wrap(String value) {
        return "\"" + value + "\"";
    }
}

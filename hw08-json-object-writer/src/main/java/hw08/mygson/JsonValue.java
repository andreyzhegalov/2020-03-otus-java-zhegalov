package hw08.mygson;

public class JsonValue {
    private final Object value;

    public JsonValue(Object value) {
        this.value = value;
    }

    public String toJson() {
        final var sb = new StringBuilder();
        if (value == null){
            sb.append("null");
        }
        else if (value.getClass().isMemberClass()) {
            sb.append(new JsonObject(value).toJson());
        } else if (value.getClass().isArray()) {
            sb.append(new JsonArray((Object[]) value).toJson());
        } else if (value.getClass().equals(String.class)) {
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

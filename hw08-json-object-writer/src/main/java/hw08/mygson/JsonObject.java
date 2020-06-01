package hw08.mygson;

public class JsonObject {
    private final Object value;
    private final String key;

    public JsonObject(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String toJson() {
        return wrap(key) + ":" + JsonValue.toJson(this.value);
    }

    private String wrap(String value) {
        return "\"" + value + "\"";
    }
}

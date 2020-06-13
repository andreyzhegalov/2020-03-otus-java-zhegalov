package hw08.mygson.json;

public class JsonArray {
    final Object[] array;

    public JsonArray(Object[] array) {
        this.array = array;
    }

    public String toJson() {
        final var sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(new JsonValue(array[i]).toJson());
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

package hw08.mygson;

public class JsonArray {
    final Object[] array;

    public JsonArray(Object[] array) {
        this.array = array;
    }

    public StringBuilder toJson(StringBuilder sb) throws IllegalArgumentException, IllegalAccessException {
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            new JsonValue(array[i]).toJson(sb);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb;
    }
}

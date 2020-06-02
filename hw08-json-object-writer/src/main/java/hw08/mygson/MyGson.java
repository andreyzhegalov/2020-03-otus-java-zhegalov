package hw08.mygson;

public class MyGson {
    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        final var jsonObject = new JsonObject(obj);
        try {
            sb = jsonObject.toJson(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

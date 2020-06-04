package hw08.mygson.json;

import hw08.mygson.object.ObjectHandler;

public class JsonObject {
    private final Object obj;

    public JsonObject(Object obj) {
        this.obj = obj;
    }

    public String toJson() {
        final var sb = new StringBuilder();
        sb.append("{");
        final var objectHandler = new ObjectHandler(this.obj);
        final var fields = objectHandler.getFields();
        for (int i = 0; i < fields.size(); i++) {
            final var field = fields.get(i);
            sb.append(wrap((field.getName())));
            sb.append(":");
            sb.append(new JsonValue(field.getObject()).toJson());
            if (i < fields.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private String wrap(String value) {
        return "\"" + value + "\"";
    }
}

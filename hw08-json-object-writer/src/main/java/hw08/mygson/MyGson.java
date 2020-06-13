package hw08.mygson;

import hw08.mygson.json.JsonValue;
import hw08.mygson.json.JsonArray;
import hw08.mygson.json.JsonObject;
import hw08.mygson.object.ObjectHandler;

public class MyGson {
    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        final var objectHandler = new ObjectHandler(obj);
        if (objectHandler.isPrimitiveWrapper() || objectHandler.isString()) {
            return new JsonValue(obj).toJson();
        }
        if (objectHandler.isArray()) {
            final Object[] array = (Object[]) objectHandler.getObject();
            return new JsonArray(array).toJson();
        }
        return new JsonObject(obj).toJson();
    }
}

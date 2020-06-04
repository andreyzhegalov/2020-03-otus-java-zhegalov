package hw08.mygson;

import hw08.mygson.json.JsonObject;

public class MyGson {
    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        return new JsonObject(obj).toJson();
    }
}

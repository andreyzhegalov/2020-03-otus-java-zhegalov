package hw08.mygson;

public class MyGson {

    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        return "{}";
    }

}

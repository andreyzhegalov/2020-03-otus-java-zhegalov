package hw08.mygson;

public class JsonValue {

    public static String toJson(Object value){
        String result = value.toString();
        if( value.getClass().isArray()){
            result = new JsonArray((Object[]) value).toJson();
        }
        if (value.getClass().equals(String.class)){
            result = wrap(result);
        }
        return result;
    }

    private static String wrap(String value) {
        return "\"" + value + "\"";
    }
}


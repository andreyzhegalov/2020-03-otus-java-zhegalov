package hw08.mygson;

public class JsonValue {
    private final Object value;

    public JsonValue(Object value) {
        this.value = value;
    }

    public StringBuilder toJson(StringBuilder sb) throws IllegalArgumentException, IllegalAccessException{
        if( value.getClass().isMemberClass()){
            new JsonObject(value).toJson(sb);
        }
        else if( value.getClass().isArray()){
            new JsonArray((Object[]) value).toJson(sb);
        }
        else if (value.getClass().equals(String.class)){
            sb.append( wrap(value.toString()) );
        }
        else {
            sb.append(value.toString());
        }
        return sb;
    }


    private static String wrap(String value) {
        return "\"" + value + "\"";
    }
}




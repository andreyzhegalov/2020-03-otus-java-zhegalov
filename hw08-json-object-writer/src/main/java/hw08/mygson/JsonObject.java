package hw08.mygson;

public class JsonObject {
    private final Object value;
    private final String key;

    public JsonObject(String key, Object value){
        this.key = key;
        this.value =value;
    }

    public String toJson(){
        String value = this.value.toString();
        if( this.value.getClass().isArray()){
            value = new JsonArray((Object[]) this.value).toJson();
        }
        if (this.value.getClass().equals(String.class)){
            value = wrap(value);
        }
        return wrap(key) + ":" + value;
    }

    public String getKey() {
        return wrap(key);
    }

    private String wrap(String value) {
        return "\"" + value + "\"";
    }
}

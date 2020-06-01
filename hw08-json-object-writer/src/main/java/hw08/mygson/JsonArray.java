package hw08.mygson;

public class JsonArray {
    final Object[] array;

    public JsonArray(Object[] array) {
        this.array = array;
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            if (array[i].getClass().equals(String.class)) {
                sb.append(wrap(array[i].toString()));
            } else {
                sb.append(array[i].toString());
            }

            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String wrap(String value) {
        return "\"" + value + "\"";
    }
}


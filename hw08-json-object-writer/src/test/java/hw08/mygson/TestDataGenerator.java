package hw08.mygson;

import com.google.gson.Gson;

class TestDataGenerator {

    public static void main(String[] args) {
        TestEmptyClass testEmpty = new TestEmptyClass();

        Gson gson = new Gson();

        String jsonNull = gson.toJson(null);
        System.out.println(jsonNull);

        String jsonEmpty = gson.toJson(testEmpty);
        System.out.println(jsonEmpty);

    }

}


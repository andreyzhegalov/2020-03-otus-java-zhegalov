package hw08.mygson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hw08.mygson.testingclasses.*;

public class MyGsonTest {
    private String getExpectedJson(Object obj) {
        return new Gson().toJson(obj);
    }

    @Test
    public void testToJsonNull() {
        assertEquals(getExpectedJson(null), new MyGson().toJson(null));
    }

    @Test
    public void testToJsonEmptyClass() {
        final var obj = new EmptyClass();
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonForBooleanTrue() {
        final var obj = new ClassWithBoolean(true);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonForBooleanFalse() {
        final var obj = new ClassWithBoolean(false);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonForInt() {
        final var obj = new ClassWithInt(1);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonForNegativeInt() {
        final var obj = new ClassWithInt(-4);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonDouble() {
        final var obj = new ClassWithDouble(1.123);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonNegativeDouble() {
        final var obj = new ClassWithDouble(-1.123);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonString() {
        final var obj = new ClassWithString("test");
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectArrayWithoutEement() {
        final Integer[] array = { 1 };
        final var obj = new ClassWithObjectArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectArrayWithOneElement() {
        final Integer[] array = { 1 };
        final var obj = new ClassWithObjectArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectArray() {
        final Integer[] array = { 1, 2, 3 };
        final var obj = new ClassWithObjectArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromPrimitiveArray() {
        final int[] array = { 1, 2, 3 };
        final var obj = new ClassWithIntegerArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromList() {
        final List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");

        final var obj = new ClassWithList(list);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromSet() {
        final Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        final var obj = new ClassWithSet(set);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromQueue() {
        final Queue<String> queue = new ArrayDeque<>();
        queue.add("first");
        queue.add("second");
        queue.add("third");
        final var obj = new ClassWithQueue(queue);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonManyPrimitiveField() {
        final var obj = new ClassWithManyField(1, true);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectWithObject() {
        final Object innerObject = new ClassWithInt(1);
        final var obj = new ClassWithOtherClass(innerObject);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectWithArrayOfObject() {
        final ClassWithInt[] array = new ClassWithInt[2];
        array[0] = new ClassWithInt(0);
        array[1] = new ClassWithInt(1);

        final var obj = new ClassWithOtherClass(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectWithNullValue() {
        final ClassWithInt[] array = new ClassWithInt[2];
        array[0] = new ClassWithInt(0);
        array[1] = null;

        final var obj = new ClassWithOtherClass(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectWithMap() {
        final Map<String, Integer> map = new HashMap<>();
        map.put("first", 1);
        map.put("second", 2);
        final var obj = new ClassWithMap(map);
        assertThrows(MyGsonException.class, () -> new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectWithConst(){
        final var obj = new ClassWithStatic();
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @ParameterizedTest
    @MethodSource("generateDataForJsonValueTest")
    void testObjectValueCases(Object o) {
        MyGson myGson = new MyGson();
        assertEquals(getExpectedJson(o), myGson.toJson(o));
    }

    private static Stream<Arguments> generateDataForJsonValueTest() {
        return Stream.of(Arguments.of(true), Arguments.of(false), Arguments.of((byte) 1), Arguments.of((short) 2f),
                Arguments.of(3), Arguments.of(4L), Arguments.of(5f), Arguments.of(6d), Arguments.of("aaa"),
                Arguments.of('b'));
    }

    @ParameterizedTest
    @MethodSource("generateDataForJsonArrayTest")
    void testObjectArrayCases(Object o) {
        MyGson myGson = new MyGson();
        assertEquals(getExpectedJson(o), myGson.toJson(o));
    }

    private static Stream<Arguments> generateDataForJsonArrayTest() {
        return Stream.of(Arguments.of(new byte[] { 1, 2, 3 }), Arguments.of(new short[] { 4, 5, 6 }),
                Arguments.of(new int[] { 7, 8, 9 }), Arguments.of(new float[] { 10f, 11f, 12f }),
                Arguments.of(new double[] { 13d, 14d, 15d }), Arguments.of(List.of(16, 17, 18)),
                Arguments.of(Collections.singletonList(19)));
    }
}

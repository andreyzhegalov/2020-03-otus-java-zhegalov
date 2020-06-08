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
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

public class MyGsonTest {
    private String getExpectedJson(Object obj) {
        return new Gson().toJson(obj);
    }

    @Test
    public void testToJsonNull() {
        assertEquals(getExpectedJson(null), new MyGson().toJson(null));
    }

    class Empty {
    }

    @Test
    public void testToJsonEmptyClass() {
        final var obj = new Empty();
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithBoolean {
        private boolean b;

        public WithBoolean(boolean b) {
            this.b = b;
        }
    }

    @Test
    public void testToJsonForBooleanTrue() {
        final var obj = new WithBoolean(true);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonForBooleanFalse() {
        final var obj = new WithBoolean(false);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithInt {
        private int i;

        public WithInt(int i) {
            this.i = i;
        }
    }

    @Test
    public void testToJsonForInt() {
        final var obj = new WithInt(1);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonForNegativeInt() {
        final var obj = new WithInt(-4);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithDouble {
        final double d;

        public WithDouble(double d) {
            this.d = d;
        }
    }

    @Test
    public void testToJsonDouble() {
        final var obj = new WithDouble(1.123);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonNegativeDouble() {
        final var obj = new WithDouble(-1.123);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithString {
        final String str;

        WithString(String str) {
            this.str = str;
        }
    }

    @Test
    public void testToJsonString() {
        final var obj = new WithString("test");
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithObjectArray {
        private Object[] array;

        WithObjectArray(Object[] array) {
            this.array = array;
        }
    }

    @Test
    public void testToJsonFromObjectArrayWithoutEement() {
        final Integer[] array = { 1 };
        final var obj = new WithObjectArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectArrayWithOneElement() {
        final Integer[] array = { 1 };
        final var obj = new WithObjectArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectArray() {
        final Integer[] array = { 1, 2, 3 };
        final var obj = new WithObjectArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithIntegerArray {
        private int[] array;

        WithIntegerArray(int[] array) {
            this.array = array;
        }
    }

    @Test
    public void testToJsonFromPrimitiveArray() {
        final int[] array = { 1, 2, 3 };
        final var obj = new WithIntegerArray(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithList {
        private List<?> list;

        WithList(List<?> list) {
            this.list = list;
        }
    }

    @Test
    public void testToJsonFromList() {
        final List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");

        final var obj = new WithList(list);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithSet {
        final Set<?> set;

        WithSet(Set<?> set) {
            this.set = set;
        }
    }

    @Test
    public void testToJsonFromSet() {
        final Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        final var obj = new WithSet(set);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithQueue {
        final Queue<?> queue;

        WithQueue(Queue<?> queue) {
            this.queue = queue;
        }
    }

    @Test
    public void testToJsonFromQueue() {
        final Queue<String> queue = new ArrayDeque<>();
        queue.add("first");
        queue.add("second");
        queue.add("third");
        final var obj = new WithQueue(queue);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithManyField {
        final int i;
        final boolean b;

        public WithManyField(int i, boolean b) {
            this.i = i;
            this.b = b;
        }
    }

    @Test
    public void testToJsonManyPrimitiveField() {
        final var obj = new WithManyField(1, true);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithOtherClass {
        final Object obj;

        public WithOtherClass(Object obj) {
            this.obj = obj;
        }
    }

    @Test
    public void testToJsonFromObjectWithObject() {
        final Object innerObject = new WithInt(1);
        final var obj = new WithOtherClass(innerObject);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectWithArrayOfObject() {
        final WithInt[] array = new WithInt[2];
        array[0] = new WithInt(0);
        array[1] = new WithInt(1);

        final var obj = new WithOtherClass(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    @Test
    public void testToJsonFromObjectWithNullValue() {
        final WithInt[] array = new WithInt[2];
        array[0] = new WithInt(0);
        array[1] = null;

        final var obj = new WithOtherClass(array);
        assertEquals(getExpectedJson(obj), new MyGson().toJson(obj));
    }

    class WithMap {
        private final Map<?, ?> map;

        public WithMap(Map<?, ?> map) {
            this.map = map;
        }
    }

    @Test
    public void testToJsonFromObjectWithMap() {
        final Map<String, Integer> map = new HashMap<>();
        map.put("first", 1);
        map.put("second", 2);
        final var obj = new WithMap(map);
        assertThrows(MyGsonException.class, () -> new MyGson().toJson(obj));
    }

    @Test
    public void tempTest() {
        Object o = List.of(16, 17, 18);
        MyGson myGson = new MyGson();
        assertEquals(getExpectedJson(o), myGson.toJson(o));
    }

    @ParameterizedTest
    @MethodSource("generateDataForCustomTest")
    void customTest(Object o){
        MyGson myGson = new MyGson();
        assertEquals(getExpectedJson(o), myGson.toJson(o));
    }

    private static Stream<Arguments> generateDataForCustomTest() {
        return Stream.of(
                null,
                Arguments.of(true), Arguments.of(false),
                Arguments.of((byte)1), Arguments.of((short)2f),
                Arguments.of(3), Arguments.of(4L), Arguments.of(5f), Arguments.of(6d),
                Arguments.of("aaa"), Arguments.of('b'),
                Arguments.of(new byte[] {1, 2, 3}),
                Arguments.of(new short[] {4, 5, 6}),
                Arguments.of(new int[] {7, 8, 9}),
                Arguments.of(new float[] {10f, 11f, 12f}),
                Arguments.of(new double[] {13d, 14d, 15d}),
                Arguments.of(List.of(16, 17, 18)),
                Arguments.of(Collections.singletonList(19))
        );
    }
}


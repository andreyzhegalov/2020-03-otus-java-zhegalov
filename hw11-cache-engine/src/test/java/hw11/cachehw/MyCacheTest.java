package hw11.cachehw;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MyCacheTest {

    @Test
    public void putWithNullKeyTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        assertDoesNotThrow(() -> cache.put(null, null));
        assertEquals(0, cache.getSize() );
    }

    @Test
    public void putExistTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        assertDoesNotThrow(() -> cache.put(1, null));
        assertDoesNotThrow(() -> cache.put(1, null));
        assertEquals(1, cache.getSize() );
    }

    @Test
    public void putNewKeyTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        assertDoesNotThrow(() -> cache.put(1, null));
        assertEquals(1, cache.getSize() );
    }

    @Test
    public void removeNullKeyTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        assertDoesNotThrow(()-> cache.remove(null));
    }

    @Test
    public void removeExistKeyTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        cache.put(1,1);
        assertDoesNotThrow(()-> cache.remove(1));
    }

    @Test
    public void removeNotExistKeyTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        assertDoesNotThrow(()-> cache.remove(1));
    }

    @Test
    public void getNullKeyTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        assertNull(cache.get(null));
    }

    @Test
    public void getExistValueTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        cache.put(1,1);
        assertEquals(1, cache.get(1));
    }

    @Test
    public void getNoExistValueTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();
        assertThrows(HwCacheExeption.class, ()-> cache.get(1));
    }

    @Test
    public void addListenerTest() {
        assertThrows(UnsupportedOperationException.class, () -> new MyCache<Object, Object>().addListener(null));
    }

    @Test
    public void removeListenerTest() {
        assertThrows(UnsupportedOperationException.class, () -> new MyCache<Object, Object>().removeListener(null));
    }

}

package hw11.cachehw;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MyCacheTest {

    @Test
    public void putTest() {
        assertThrows(UnsupportedOperationException.class, () -> new MyCache<Object, Object>().put(null, null));
    }

    @Test
    public void removeTest() {
        assertThrows(UnsupportedOperationException.class, () -> new MyCache<Object, Object>().remove(null));
    }

    @Test
    public void getTest() {
        assertNull(new MyCache<>().get(null));
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

package hw11.cachehw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class AutoRemoveTest {

    @Test
    public void autoRemoveRefListenerTest() {
        MyCache<Integer, Integer> cache = new MyCache<>();

        final int LISTENER_CNT = 100000;
        for (int i = 0; i < LISTENER_CNT; i++) {
            var listener = new HwListener<Integer, Integer>() {
                @Override
                public void notify(Integer key, Integer value, String action) {
                }
            };
            cache.addListener(listener);
        }
        assertTrue(cache.getListenerCnt() < LISTENER_CNT);
    }

    @Test
    public void autoClearCacheTest() throws InterruptedException {
        MyCache<String, Integer> cache = new MyCache<>();
        String key = new String("1");
        cache.put(key, 1);
        key = null;
        assertEquals(1, cache.getSize());
        System.gc();
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        assertEquals(0, cache.getSize());
    }
}

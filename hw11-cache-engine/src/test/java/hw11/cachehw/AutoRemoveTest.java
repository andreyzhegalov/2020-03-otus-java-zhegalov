package hw11.cachehw;


import static org.junit.jupiter.api.Assertions.assertTrue;

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
}


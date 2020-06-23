package hw11.cachehw;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCache<K, V> implements HwCache<K, V> {
    private static Logger logger = LoggerFactory.getLogger(MyCache.class);
    private final WeakHashMap<K, V> cache = new WeakHashMap<>();

    private final ReferenceQueue<HwListener<K, V>> refQueue = new ReferenceQueue<>();
    private final List<SoftReference<HwListener<K, V>>> listeners = new ArrayList<>();

    public MyCache() {
        new Thread(() -> {
            logger.debug("Listener reference cleaning  thread  is started");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final var removedRef = refQueue.remove();
                    if (!listeners.remove(removedRef)) {
                        logger.error("Error remove reference to listener");
                    }
                    logger.debug("Listener reference cleaned");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    @Override
    public void put(K key, V value) {
        logger.debug("Put {} : {}", key, value);
        if (key == null) {
            return;
        }
        cache.put(key, value);
        listeners.stream().forEach(l -> {
            if (l.get() != null) {
                l.get().notify(key, value, "put");
            }
        });
    }

    @Override
    public void remove(K key) {
        logger.debug("Remove key {}", key);
        if (key == null) {
            return;
        }
        final var value = cache.remove(key);
        listeners.stream().forEach(l -> {
            if (l.get() != null) {
                l.get().notify(key, value, "remove");
            }
        });
    }

    @Override
    public V get(K key) {
        logger.debug("Get key {} ", key);
        if (key == null) {
            return null;
        }
        if (!cache.containsKey(key)) {
            throw new HwCacheExeption("Key " + key + " not exist in the cache");
        }
        final var value = cache.get(key);
        listeners.stream().forEach(l -> {
            if (l.get() != null) {
                l.get().notify(key, value, "get");
            }
        });
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        logger.debug("Add listener {}", listener);
        if (null == listener) {
            throw new HwCacheExeption("New listener is null");
        }
        if (!listeners.add(new SoftReference<>(listener, refQueue))) {
            throw new HwCacheExeption("Could not add new listener");
        }
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        logger.debug("Remove listener {}", listener);
        if (null == listener) {
            return;
        }
        if (0 == getListenerCnt()) {
            throw new HwCacheExeption("Remove failed. Has no one listener");
        }
        final var refListener = listeners.stream().filter(l -> l.get().equals(listener)).findFirst();
        listeners.remove(refListener.get());
    }

    public int getSize() {
        return cache.size();
    }

    public int getListenerCnt() {
        return listeners.size();
    }
}

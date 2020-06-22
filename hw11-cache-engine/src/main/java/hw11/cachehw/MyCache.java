package hw11.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCache<K, V> implements HwCache<K, V> {
    private static Logger logger = LoggerFactory.getLogger(MyCache.class);
    private final WeakHashMap<K, V> cache = new WeakHashMap<>();

    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        logger.debug("put {} : {}", key, value);
        if (key == null) {
            return;
        }
        cache.put(key, value);
        listeners.stream().forEach(l -> l.notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        logger.debug("remove key {}", key);
        if (key == null) {
            return;
        }
        final var value = cache.remove(key);
        listeners.stream().forEach(l -> l.notify(key, value, "remove"));
    }

    @Override
    public V get(K key) {
        logger.debug("get key {} ", key);
        if (key == null) {
            return null;
        }
        if (!cache.containsKey(key)) {
            throw new HwCacheExeption("Key " + key + " not exist in the cache");
        }
        final var value = cache.get(key);
        listeners.stream().forEach(l -> l.notify(key, value, "get"));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        logger.debug("add listener {}", listener);
        if (null == listener) {
            throw new HwCacheExeption("new listener is null");
        }
        if (!listeners.add(listener)) {
            throw new HwCacheExeption("could not add new listener");
        }
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        logger.debug("remove listener {}", listener);
        if (null == listener) {
            return;
        }
        if (0 == getListenerCnt()) {
            throw new HwCacheExeption("Listener not found for delete");
        }
        listeners.remove(listener);
    }

    public int getSize() {
        return cache.size();
    }

    public int getListenerCnt() {
        return listeners.size();
    }
}

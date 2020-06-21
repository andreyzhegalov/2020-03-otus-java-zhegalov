package hw11.cachehw;

import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCache<K, V> implements HwCache<K, V> {
    private static Logger logger = LoggerFactory.getLogger(MyCache.class);
    private final WeakHashMap<K, V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        logger.debug("put {} : {}", key, value);
        if( key == null ){
            return;
        }
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        logger.debug("remove key {}", key);
        if(key == null){
            return;
        }
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        logger.debug("get key {} ", key);
        if( key == null){
            return null;
        }
        if (!cache.containsKey(key)){
            logger.debug("key {} not exist in the cache", key);
            throw new HwCacheExeption("Key "+ key + " not exist in the cache");
        }
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        throw new UnsupportedOperationException();
    }

    public int getSize(){
        return cache.size();
    }
}

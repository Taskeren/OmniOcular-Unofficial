package me.exz.omniocular.util;

import java.util.HashMap;
import java.util.Map;

public class CacheMap<K, V> {

    private final Map<K, Integer> cacheIndex = new HashMap<>();
    private final K[] cacheIndexInverse;
    private final V[] cache;
    private int index = 0;
    private final int MAXN;

    public CacheMap(int maxn) {
        MAXN = maxn;
        cache = (V[]) new Object[MAXN];

        cacheIndexInverse = (K[]) new Object[MAXN];
    }

    public boolean contains(K key) {
        return cacheIndex.containsKey(key);
    }

    public V get(K key) {
        return cache[cacheIndex.get(key)];
    }

    public void put(K key, V value) {
        if (index == MAXN) index = 0;
        if (contains(key)) cacheIndex.remove(cacheIndexInverse[index]);
        cache[index] = value;
        cacheIndex.put(key, index);
        cacheIndexInverse[index] = key;
        index++;
    }

    public void clear() {
        index = 0;
        cacheIndex.clear();
    }
}

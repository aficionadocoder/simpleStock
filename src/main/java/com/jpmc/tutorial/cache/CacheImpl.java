package com.jpmc.tutorial.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by manish on 9/15/2015.
 */
public class CacheImpl<K, V> implements Cache<K, V> {

    private Map<K, V> map;

    public CacheImpl() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public V get(final K k) {
        return map.get(k);
    }

    @Override
    public void put(final K k, final V v) {
        map.put(k, v);
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public boolean isPresent(final K k) {
        return map.containsKey(k);
    }
}

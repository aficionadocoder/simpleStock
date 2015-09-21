package com.jpmc.tutorial.cache;

import java.util.Collection;
import java.util.List;

/**
 * Created by manish on 9/15/2015.
 */
public interface Cache<K,V> {

    V get(K k);

    void put(K k, V v);

    Collection<V> values();

    boolean isPresent(K k);
}

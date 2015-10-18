package com.lcsc.hackathon.kinectcontroller.emulation.reactions.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jake on 10/18/2015.
 *
 * Each Reaction will have different needs. This is meant to hold the information needed for each
 * different kind of reaction.
 */
public class ReactionConfig<K,V> implements Map<K,V> {
    //children classes will put their information here.
    protected Map<K, V> _config;

    public ReactionConfig() {
        _config = new HashMap<K,V>();
    }

    @Override
    public int size() {
        return _config.size();
    }

    @Override
    public boolean isEmpty() {
        return _config.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return _config.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return _config.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return _config.get(key);
    }

    @Override
    public V put(K key, V value) {
        return _config.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return _config.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        _config.putAll(m);
    }

    @Override
    public void clear() {
        _config.clear();
    }

    @Override
    public Set<K> keySet() {
        return _config.keySet();
    }

    @Override
    public Collection<V> values() {
        return _config.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return _config.entrySet();
    }
}

/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

By using Esper without their commercial license we are also required to release our software under
a GPL license.

Copyright (C) 2015  Jacob Waffle, Ryan Spiekerman and Josh Rogers

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.wafflesoft.kinectcontroller.emulation.reactions.config;

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

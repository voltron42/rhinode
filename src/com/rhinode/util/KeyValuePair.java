package com.rhinode.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyValuePair<K, V> {

	private final K key;
	private final V value;

	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public static <K,V> Map<K,V> map(List<KeyValuePair<K,V>> pairs) {
		Map<K,V> out = new HashMap<K,V>();
		for (KeyValuePair<K,V> pair : pairs) {
			out.put(pair.getKey(), pair.getValue());
		}
		return out;
	}
}

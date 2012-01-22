/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.backend;

import org.krypsis.gwt.store.client.StoreBackend;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * The memory backend is usefull whenever you need to test your
 * implementation and the real backend is not available.
 */
public class MemoryBackend implements StoreBackend {
  private final Map<String, String> storage = new HashMap<String, String>();

  public void clear() {
    storage.clear();
  }

  public String get(String key) {
    return storage.get(key);
  }

  public String key(int index) {
    return new ArrayList<String>(storage.keySet()).get(index);
  }

  public void put(String key, String value) {
    storage.put(key, value);
  }

  public String remove(String key) {
    return storage.remove(key);
  }

  public int size() {
    return storage.size();
  }
}

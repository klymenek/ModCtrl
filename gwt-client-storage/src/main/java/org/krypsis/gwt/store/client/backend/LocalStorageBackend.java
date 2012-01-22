/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.backend;

import org.krypsis.gwt.store.client.StoreBackend;
import com.google.code.gwt.storage.client.Storage;

/**
 * The local storage backend uses browser LocalStorage
 * - described in HTML5
 */
public class LocalStorageBackend implements StoreBackend {
  private final Storage storage;

  public LocalStorageBackend(Storage storage) {
    this.storage = storage;
  }

  public void clear() {
    storage.clear();
  }

  public String get(String key) {
    return storage.getItem(key);
  }

  public String key(int index) {
    return storage.key(index);
  }

  public void put(String key, String value) {
    storage.setItem(key, value);
  }

  public String remove(String key) {
    final String result = storage.getItem(key);
    storage.removeItem(key);
    return result;
  }

  public int size() {
    return storage.getLength();
  }
}

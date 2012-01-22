/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client;

/**
 * A store backend is an interface to a concrete saving
 * technology. 
 */
public interface StoreBackend {

  /**
   * Puts the value to the storage and associates it with the key.
   *
   * @param key The key
   * @param value The value to store
   */
  public void put(String key, String value);

  /**
   * Retrievs the value that is associated with the given key.
   *
   * @param key The key
   * @return The value or null if no such data was found
   */
  public String get(String key);

  /**
   * Removes the data from the storage
   *
   * @param key The associated key
   * @return The stored data or null 
   */
  public String remove(String key);

  /**
   * Clears the entire store
   */
  public void clear();

  /**
   * @return The store size (How many keys)
   */
  public int size();

  /**
   * Returns the key name on the given position
   *
   * @param index The position
   * @return The key or null, of the is no key on the position
   */
  public String key(int index);
}

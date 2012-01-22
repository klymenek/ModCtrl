/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client;

/**
 * The matcher is used to filter instances.
 * See {@link org.krypsis.gwt.store.client.Store#findAll(Matcher)} for more information
 * on quering the store.
 *
 * @param <E> The store type.
 */
public interface Matcher<E> {

  /**
   * Return true if the criteria matches the given instance.
   * Otherwise false.
   *
   * @param instance A stored instance
   * @return True if the instance matches the criteria, otherwise false.
   */
  public boolean match(E instance);
}

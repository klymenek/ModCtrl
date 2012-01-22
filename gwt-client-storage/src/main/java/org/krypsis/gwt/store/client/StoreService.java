/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client;

import com.google.gwt.event.shared.HandlerManager;

/**
 * Use this service to request stores
 *
 * @param <E> The super type of the store entity.
 */
public interface StoreService<E> {

  /**
   * Returns the store for the given class. If this store is requested
   * a second time, it will be returned from cache.
   *
   * @param clazz The entity class to create the store for
   * @param namespace The entity namespace
   * @param <T> The Entity type
   * @return The store
   */
  public <T extends E> Store<T> getStore(Class<T> clazz, String namespace);

  /**
   * @return The event bus that can be used to receive store events.
   */
  public HandlerManager getEventBus();
  
}

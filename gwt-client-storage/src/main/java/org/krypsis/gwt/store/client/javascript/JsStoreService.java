/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.javascript;

import com.google.gwt.event.shared.HandlerManager;
import org.krypsis.gwt.store.client.Store;
import org.krypsis.gwt.store.client.StoreBackend;
import org.krypsis.gwt.store.client.StoreService;

import java.util.HashMap;
import java.util.Map;

/**
 * Use the store service to create stores and to register
 */
public class JsStoreService implements StoreService<JsStorable> {
  private Map<String, Store<? extends JsStorable>> stores = new HashMap<String, Store<? extends JsStorable>>();
  private final StoreBackend backend;
  private final HandlerManager eventBus;

  public JsStoreService(StoreBackend backend) {
    this(backend, new HandlerManager(null));
  }

  public JsStoreService(StoreBackend backend, HandlerManager eventBus) {
    this.backend = backend;
    this.eventBus = eventBus;
  }

  public <T extends JsStorable> Store<T> getStore(Class<T> clazz, String name) {
    if (!stores.containsKey(name)) {
      final Store<T> store = new JsStore<T>(name, backend, eventBus);
      stores.put(name, store);
    }

    //noinspection unchecked
    return (Store<T>) stores.get(name);
  }

  public HandlerManager getEventBus() {
    return eventBus;
  }
}

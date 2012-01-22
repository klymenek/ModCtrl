package org.krypsis.gwt.store.example.client.gin;

import com.google.gwt.event.shared.HandlerManager;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.krypsis.gwt.store.client.StoreBackend;
import org.krypsis.gwt.store.client.StoreService;
import org.krypsis.gwt.store.client.javascript.JsStorable;
import org.krypsis.gwt.store.client.javascript.JsStoreService;

@Singleton
public class JsStoreServiceProvider implements Provider<StoreService<JsStorable>> {
  private final HandlerManager eventBus;
  private final StoreBackend backend;

  @Inject
  public JsStoreServiceProvider(HandlerManager eventBus, StoreBackend backend) {
    this.eventBus = eventBus;
    this.backend = backend;
  }

  public StoreService<JsStorable> get() {
    return new JsStoreService(backend, eventBus);
  }
}

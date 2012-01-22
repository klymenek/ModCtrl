/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import org.krypsis.gwt.store.client.Store;

public abstract class StoreEvent<H extends EventHandler> extends GwtEvent<H> {
  private final Store store;

  protected StoreEvent(Store store) {
    this.store = store;
  }

  public Store getStore() {
    return store;
  }
}
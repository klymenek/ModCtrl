/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;
import com.google.gwt.event.shared.EventHandler;

public abstract class InstanceEvent<I, H extends EventHandler> extends StoreEvent<H> {
  private final I instance;

  protected InstanceEvent(Store store, I instance) {
    super(store);
    this.instance = instance;
  }

  public I getInstance() {
    return instance;
  }
}

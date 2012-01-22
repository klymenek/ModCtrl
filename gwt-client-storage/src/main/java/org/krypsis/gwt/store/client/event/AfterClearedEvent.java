/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class AfterClearedEvent extends StoreEvent<AfterClearedEventHandler> {
  public static final Type<AfterClearedEventHandler> TYPE = new Type<AfterClearedEventHandler>();

  public AfterClearedEvent(Store store) {
    super(store);
  }

  public Type<AfterClearedEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(AfterClearedEventHandler handler) {
    handler.onEvent(this);
  }
}
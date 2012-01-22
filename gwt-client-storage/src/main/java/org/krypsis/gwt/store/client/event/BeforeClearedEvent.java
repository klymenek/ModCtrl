/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class BeforeClearedEvent extends StoreEvent<BeforeClearedEventHandler> {
  public static final Type<BeforeClearedEventHandler> TYPE = new Type<BeforeClearedEventHandler>();

  public BeforeClearedEvent(Store store) {
    super(store);
  }

  public Type<BeforeClearedEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(BeforeClearedEventHandler handler) {
    handler.onEvent(this);
  }
}
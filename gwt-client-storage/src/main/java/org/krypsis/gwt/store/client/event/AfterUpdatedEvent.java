/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class AfterUpdatedEvent<I> extends InstanceEvent<I, AfterUpdatedEventHandler> {
  public static final Type<AfterUpdatedEventHandler> TYPE = new Type<AfterUpdatedEventHandler>();

  public AfterUpdatedEvent(Store store, I instance) {
    super(store, instance);
  }

  public Type<AfterUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @SuppressWarnings({"unchecked"})
  protected void dispatch(AfterUpdatedEventHandler handler) {
    handler.onEvent(this);
  }
}
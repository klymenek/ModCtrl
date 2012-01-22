/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class AfterInsertedEvent<I> extends InstanceEvent<I, AfterInsertedEventHandler> {
  public static final Type<AfterInsertedEventHandler> TYPE = new Type<AfterInsertedEventHandler>();

  public AfterInsertedEvent(Store store, I instance) {
    super(store, instance);
  }

  public Type<AfterInsertedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @SuppressWarnings({"unchecked"})
  protected void dispatch(AfterInsertedEventHandler handler) {
    handler.onEvent(this);
  }
}
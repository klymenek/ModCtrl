/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class BeforeInsertedEvent<I> extends InstanceEvent<I, BeforeInsertedEventHandler> {
  public static final Type<BeforeInsertedEventHandler> TYPE = new Type<BeforeInsertedEventHandler>();

  public BeforeInsertedEvent(Store store, I instance) {
    super(store, instance);
  }

  public Type<BeforeInsertedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @SuppressWarnings({"unchecked"})
  protected void dispatch(BeforeInsertedEventHandler handler) {
    handler.onEvent(this);
  }
}

/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class BeforeDeletedEvent<I> extends InstanceEvent<I, BeforeDeletedEventHandler> {
  public static final Type<BeforeDeletedEventHandler> TYPE = new Type<BeforeDeletedEventHandler>();

  public BeforeDeletedEvent(Store store, I instance) {
    super(store, instance);
  }

  public Type<BeforeDeletedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @SuppressWarnings({"unchecked"})
  protected void dispatch(BeforeDeletedEventHandler handler) {
    handler.onEvent(this);
  }
}
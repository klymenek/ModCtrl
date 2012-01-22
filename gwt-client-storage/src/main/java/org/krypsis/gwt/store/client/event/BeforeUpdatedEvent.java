/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class BeforeUpdatedEvent<I> extends InstanceEvent<I, BeforeUpdatedEventHandler> {
  public static final Type<BeforeUpdatedEventHandler> TYPE = new Type<BeforeUpdatedEventHandler>();

  public BeforeUpdatedEvent(Store store, I instance) {
    super(store, instance);
  }

  public Type<BeforeUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @SuppressWarnings({"unchecked"})
  protected void dispatch(BeforeUpdatedEventHandler handler) {
    handler.onEvent(this);
  }
}
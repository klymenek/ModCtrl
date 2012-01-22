/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class AfterDeletedEvent<I> extends InstanceEvent<I, AfterDeletedEventHandler> {
  public static final Type<AfterDeletedEventHandler> TYPE = new Type<AfterDeletedEventHandler>();

  public AfterDeletedEvent(Store store, I instance) {
    super(store, instance);
  }

  @SuppressWarnings({"unchecked"})
  protected void dispatch(AfterDeletedEventHandler handler) {
    handler.onEvent(this);
  }

  public Type<AfterDeletedEventHandler> getAssociatedType() {
    return TYPE;
  }
}
/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class AfterSavedEvent<I> extends InstanceEvent<I, AfterSavedEventHandler> {
  public static final Type<AfterSavedEventHandler> TYPE = new Type<AfterSavedEventHandler>();

  public AfterSavedEvent(Store store, I instance) {
    super(store, instance);
  }

  public Type<AfterSavedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @SuppressWarnings({"unchecked"})
  protected void dispatch(AfterSavedEventHandler handler) {
    handler.onEvent(this);
  }
}
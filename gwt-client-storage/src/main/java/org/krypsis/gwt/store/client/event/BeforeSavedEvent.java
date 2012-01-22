/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import org.krypsis.gwt.store.client.Store;

public class BeforeSavedEvent<I> extends InstanceEvent<I, BeforeSavedEventHandler> {
  public static final Type<BeforeSavedEventHandler> TYPE = new Type<BeforeSavedEventHandler>();

  public BeforeSavedEvent(Store store, I instance) {
    super(store, instance);
  }

  public Type<BeforeSavedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @SuppressWarnings({"unchecked"})
  protected void dispatch(BeforeSavedEventHandler handler) {
    handler.onEvent(this);
  }
}
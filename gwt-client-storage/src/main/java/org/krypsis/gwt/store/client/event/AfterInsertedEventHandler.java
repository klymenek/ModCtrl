/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AfterInsertedEventHandler<T> extends EventHandler {

  public void onEvent(AfterInsertedEvent<T> event);
}

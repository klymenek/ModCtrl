/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.javascript;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class JsStorable extends JavaScriptObject {
  protected JsStorable() {}

  /**
   * @return The unique identifier
   */
  public final native String getId() /*-{
    return this.id;
  }-*/;

  /**
   * Sets the unique identifier
   *
   * @param id The identifier
   */
  public final native void setId(String id) /*-{
    this.id = id; 
  }-*/;

  /**
   * @return True if this instance is not saved and therefore has no identifier.
   */
  public final boolean isNew() {
    return getId() == null || getId().isEmpty();
  }
}

/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.javascript;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JavaScriptObject;

public class MutableJsArray<T extends JavaScriptObject> extends JsArray<T> {

  protected MutableJsArray() { }

  /**
   * Removes the item on the specified position and
   *
   * @param from The position
   */
  public final void remove(int from) {
    remove(from, null);
  }

  /**
   * Removes one or more elements from this array.
   *
   * @param from The position from.
   * @param to The position to
   * @see 'http://ejohn.org/blog/javascript-array-remove/'
   */
  public final native void remove(int from, Integer to) /*-{
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    this.push.apply(this, rest);
  }-*/;
}

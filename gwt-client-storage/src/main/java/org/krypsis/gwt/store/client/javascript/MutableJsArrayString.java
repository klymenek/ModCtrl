/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.javascript;

import com.google.gwt.core.client.JsArrayString;

public class MutableJsArrayString extends JsArrayString {
  protected MutableJsArrayString() { }

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

  public final int indexOf(String element) {
    return indexOf(element, 0);
  }

  /**
   * Returns the index of the given element or null, if there is
   * no such element
   *
   * (Copied from prototype)
   *
   * @param element The element
   * @param i Start index to search from
   * @return The index or -1 of element not in array
   */
  public final native int indexOf(String element, int i) /*-{
    i || (i = 0);
    var length = this.length;
    if (i < 0) i = length + i;
    for (; i < length; i++) {
      if (this[i] === element) {
        return i;
      }
    }
    return -1;
  }-*/;

  public final native void add(String element) /*-{
    this.push(element);
  }-*/;

  /**
   * Clears the entire array.
   */
  public final native void clear() /*-{
    this.length = 0;
  }-*/;
}

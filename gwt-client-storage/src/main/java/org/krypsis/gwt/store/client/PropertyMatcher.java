/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client;

/**
 * A property matcher matches defined criteria and returnes
 * a particalur property, not the entire instance.
 *
 * @param <E> The instance type
 * @param <T> The property value type
 */
public interface PropertyMatcher<E, T> extends Matcher<E> {

  /**
   * Returns the value of the property
   *
   * @param instance The matched instance
   * @return The value if the property
   */
  public T getValueOf(E instance);
}

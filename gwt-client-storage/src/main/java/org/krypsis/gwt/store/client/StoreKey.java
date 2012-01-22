/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client;

public class StoreKey {
  private final String name;

  public StoreKey(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("StoreKey");
    sb.append("{name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

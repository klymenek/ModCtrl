package org.krypsis.gwt.store.example.shared;

import java.io.Serializable;

public class Entity implements Serializable {
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append("Entity");
    sb.append("{id='").append(id).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

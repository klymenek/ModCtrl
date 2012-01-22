package org.krypsis.gwt.store.example.shared;

import java.util.List;
import java.util.ArrayList;

public class Airline extends Entity {
  private List<String> airplaneIds = new ArrayList<String>();
  private String name;

  public List<String> getAirplaneIds() {
    return airplaneIds;
  }

  public void setAirplaneIds(List<String> airplaneIds) {
    this.airplaneIds = airplaneIds;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append("Airline");
    sb.append("{airplaneIds=").append(airplaneIds);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

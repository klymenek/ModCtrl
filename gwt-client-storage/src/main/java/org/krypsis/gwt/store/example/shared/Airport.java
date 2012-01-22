package org.krypsis.gwt.store.example.shared;

public class Airport extends Entity {
  private String country;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append("Airport");
    sb.append("{country='").append(country).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

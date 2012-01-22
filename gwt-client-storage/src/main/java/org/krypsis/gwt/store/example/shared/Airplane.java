package org.krypsis.gwt.store.example.shared;

public class Airplane extends Entity {
  private String flightNumber;
  private String airlineId;
  private String sourceAirportId;
  private String destinationAirportId;

  public String getAirlineId() {
    return airlineId;
  }

  public void setAirlineId(String airlineId) {
    this.airlineId = airlineId;
  }

  public String getDestinationAirportId() {
    return destinationAirportId;
  }

  public void setDestinationAirportId(String destinationAirportId) {
    this.destinationAirportId = destinationAirportId;
  }

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public String getSourceAirportId() {
    return sourceAirportId;
  }

  public void setSourceAirportId(String sourceAirportId) {
    this.sourceAirportId = sourceAirportId;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append("Airplane");
    sb.append("{airlineId='").append(airlineId).append('\'');
    sb.append(", flightNumber='").append(flightNumber).append('\'');
    sb.append(", sourceAirportId='").append(sourceAirportId).append('\'');
    sb.append(", destinationAirportId='").append(destinationAirportId).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

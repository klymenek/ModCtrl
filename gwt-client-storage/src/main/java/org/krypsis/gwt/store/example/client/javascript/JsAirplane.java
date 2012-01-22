package org.krypsis.gwt.store.example.client.javascript;

import org.krypsis.gwt.store.client.javascript.JsStorable;

/**
 * A plane belongs to an airline, has source and destination
 * airport and has a flight number.
 */
public class JsAirplane extends JsStorable {
  public static final String NAMESPACE = "org.krypsis.Airplane";

  protected JsAirplane() {}

  /**
   * @return The current flight number of this plane
   */
  public final native String getFlightNumber() /*-{
    return this.flightNumber;
  }-*/;

  /**
   * Sets the current flight number
   *
   * @param number The current flight number
   */
  public final native void setFlightNumber(String number) /*-{
    this.flightNumber = number;
  }-*/;

  /**
   * @return The airline id of the airline this plane belongs to
   */
  public final native String getAirlineId() /*-{
    return this.airlineId;
  }-*/;

  /**
   * Sets the airline id of the airline this plane belongs to
   *
   * @param id Airline id
   */
  public final native void setAirlineId(String id) /*-{
    this.airlineId = id;
  }-*/;

  /**
   * @return The id of the source airport
   */
  public final native String getSourceAirportId() /*-{
    return this.sourceAirportId;
  }-*/;

  /**
   * Sets the id of the source airport
   *
   * @param id The id of the source airport
   */
  public final native void setSourceAirportId(String id) /*-{
    this.sourceAirportId = id;
  }-*/;

  /**
   * @return The id of the destination airport
   */
  public final native String getDestinationAirportId() /*-{
    return this.destinationAirportId;
  }-*/;

  /**
   * Sets the id of the destination airport
   *
   * @param id The id of the destination airport
   */
  public final native void setDestinationAirportId(String id) /*-{
    this.destinationAirportId = id;
  }-*/;

  public static JsAirplane build() {
    return build("{}");
  }
  public static native JsAirplane build(String json) /*-{ return eval('(' + json + ')'); }-*/;
}

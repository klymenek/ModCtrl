package org.krypsis.gwt.store.example.client.javascript;

import org.krypsis.gwt.store.client.javascript.JsStorable;

public class JsAirport extends JsStorable {
  public static final String NAMESPACE = "org.krypsis.Airport";

  protected JsAirport() {}

  /**
   * @return The country name of this airport
   */
  public final native String getCountry() /*-{
    return this.country;
  }-*/;

  /**
   * Sets the country name of this airport
   *
   * @param country The country name
   */
  public final native void setCountry(String country) /*-{
    this.country = country;
  }-*/;

  /**
   * @return Builds and returns a new instance of an Airport.
   */
  public static JsAirport build() {
    return build("{}");
  }

  public static native JsAirport build(String json) /*-{
    return eval('(' + json + ')');
  }-*/;
}

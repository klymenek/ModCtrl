package org.krypsis.gwt.store.example.client.javascript;

import org.krypsis.gwt.store.client.javascript.JsStorable;
import org.krypsis.gwt.store.client.javascript.MutableJsArrayString;

import java.util.List;
import java.util.ArrayList;

/**
 * An airline has a name and a set of airplanes
 */
public class JsAirline extends JsStorable {
  public static final String NAMESPACE = "org.krypsis.Airline";

  protected JsAirline() {}

  /**
   * @return The ids of all airplanes belongs to this airline
   */
  public final List<String> getAirplaneIds() {
    final MutableJsArrayString arrayString = getAirplaneIds0();
    final List<String> result = new ArrayList<String>();
    for (int i = 0; i < arrayString.length(); i++) {
      result.add(arrayString.get(i));
    }
    return result;
  }

  /**
   * Sets the given airplane ids
   *
   * @param airplaneIds The airplane ids
   */
  public final void setAirplaneIds(List<String> airplaneIds) {
    final MutableJsArrayString ids = getAirplaneIds0();
    ids.clear();
    for (String id : airplaneIds) {
      ids.add(id);
    }
  }


  /**
   * Adds an additional plane to the airline.
   * Will not be added twice.
   *
   * @param airplane The plane to add
   */
  public final void addAirplane(JsAirplane airplane) {
    addAirplaneId(airplane.getId());
  }

  /**
   * Adds an airplane id, if not already added.
   *
   * @param id The airplane to add
   */
  public final void addAirplaneId(String id) {
    if (getAirplaneIds0().indexOf(id) < 0) {
      getAirplaneIds0().add(id);
    }
  }

  /**
   * Removes the given airplaine from this airline
   *
   * @param airplane The plane to remove
   */
  public final void removeAirplane(JsAirplane airplane) {
    removeAirplaneId(airplane.getId());
  }

  /**
   * Removes the given airplane id from the list.
   *
   * @param id The airplane id
   */
  public final void removeAirplaneId(String id) {
    final MutableJsArrayString ids = getAirplaneIds0();
    final int index = ids.indexOf(id);
    if (index < 0) {
      return;
    }
    ids.remove(index);
  }

  public static JsAirline build() {
    return build("{}");
  }

  /**
   * @return The name of this airline
   */
  public final native String getName() /*-{
    return this.name;
  }-*/;

  /**
   * Sets the name of this airline
   *
   * @param name Name of this airline
   */
  public final native void setName(String name) /*-{
    this.name = name;
  }-*/;

  /**
   * @return The ids of all airlplanes that belongs to this airline.
   */
  public final native MutableJsArrayString getAirplaneIds0() /*-{
    if (!this.airplaneIds) { this.airplaneIds = []; } return this.airplaneIds;
  }-*/;

  public static native JsAirline build(String json) /*-{ return eval('(' + json + ')'); }-*/;

}

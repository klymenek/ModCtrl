package org.krypsis.gwt.store.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.krypsis.gwt.store.example.shared.Airline;
import org.krypsis.gwt.store.example.shared.Airplane;

import java.util.List;

public interface AirlineServiceAsync {

  /**
   * Creates or updates the airline
   *
   * @param airline The airline to save
   * @param callback The callback with the saved airline or an error.
   */
  public void save(Airline airline, AsyncCallback<Airline> callback);

  /**
   * Loads the airline with the given id.
   * If airline does not exists, null will be returned
   *
   * @param id The airline id
   * @param callback The callbaclk with the result.
   */
  public void load(String id, AsyncCallback<Airline> callback);

  /**
   * Deletes the airline with the given id. The airline of the associated
   * airplanes will be set to null.
   *
   * @param id The id of the airplane.
   * @param callback The callback with the deleted airline
   */
  public void delete(String id, AsyncCallback<Airline> callback);

  /**
   * Returns a list of all airplanes that is associated with the airline
   *
   * @param id The airline id
   * @param callback The callback with the airplanes
   */
  public void getAirplanes(String id, AsyncCallback<List<Airplane>> callback);

  /**
   * Adds an airplane to the airline
   *
   * @param id The id of the airline
   * @param airplane The airplane to add
   * @param callback The callback with the affected airline
   */
  public void addAirplane(String id, Airplane airplane, AsyncCallback<Airline> callback);

  /**
   * Removes the airplane from airline
   *
   * @param id The airline id
   * @param airplane The airplane to remove
   * @param callback The callback with the affected airline
   */
  public void removeAirplane(String id, Airplane airplane, AsyncCallback<Airline> callback);

  /**
   * Search for an airline with the given name.
   * The first match will be returned. It there is no such airline
   * null will be returned.
   *
   * @param name The airline name
   * @param callback The callback with the result
   */
  public void findByName(String name, AsyncCallback<Airline> callback);
}
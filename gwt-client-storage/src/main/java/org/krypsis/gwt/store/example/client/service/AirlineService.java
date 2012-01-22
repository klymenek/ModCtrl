package org.krypsis.gwt.store.example.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import org.krypsis.gwt.store.example.shared.Airline;
import org.krypsis.gwt.store.example.shared.Airplane;

import java.util.List;

public interface AirlineService extends RemoteService {
  /**
   * Creates or updates the airline
   *
   * @param airline  The airline to save
   */
  public Airline save(Airline airline);

  /**
   * Loads the airline with the given id.
   * If airline does not exists, null will be returned
   *
   * @param id       The airline id
   */
  public Airline load(String id);

  /**
   * Deletes the airline with the given id and all associated airplanes.
   *
   * @param id       The id of the airplane.
   */
  public Airline delete(String id);

  /**
   * Returns a list of all airplanes that is associated with the airline
   *
   * @param id       The airline id
   */
  public List<Airplane> getAirplanes(String id);

  /**
   * Adds an airplane to the airline
   *
   * @param id       The id of the airline
   * @param airplane The airplane to add
   */
  public Airline addAirplane(String id, Airplane airplane);

  /**
   * Removes the airplane from airline
   *
   * @param id       The airline id
   * @param airplane The airplane to remove
   */
  public Airline removeAirplane(String id, Airplane airplane);

  /**
   * Search for an airline with the given name.
   * The first match will be returned. It there is no such airline
   * null will be returned.
   *
   * @param name     The airline name
   */
  public Airline findByName(String name);


  /**
   * Utility/Convenience class.
   * Use AirlineService.App.getInstance() to access static instance of AirlineServiceAsync
   */
  public static class App {
    private static final AirlineServiceAsync ourInstance;

    static {
      ourInstance = (AirlineServiceAsync) GWT.create(AirlineService.class);
      ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "org.krypsis.gwt.store.example.Example/AirlineServiceAsync");
    }

    public static AirlineServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
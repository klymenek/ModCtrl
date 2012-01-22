package org.krypsis.gwt.store.example.client.service;

import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.core.client.GWT;
import org.krypsis.gwt.store.example.shared.Airplane;
import org.krypsis.gwt.store.example.shared.Airport;

import java.util.List;

public interface AirplaneService extends RemoteService {
  public Airplane save(Airplane airplane);

  public Airplane load(String id);

  public Airplane delete(String id);

  public List<Airplane> findBySourceAirport(Airport airport);

  public List<Airplane> findByDestinationAirport(Airport airport);

  /**
   * Utility/Convenience class.
   * Use AirplaneService.App.getInstance() to access static instance of AirplaneServiceAsync
   */
  public static class App {
    private static final AirplaneServiceAsync ourInstance;

    static {
      ourInstance = (AirplaneServiceAsync) GWT.create(AirplaneService.class);
      ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "org.krypsis.gwt.store.example.Example/AirplaneService");
    }

    public static AirplaneServiceAsync getInstance() {
      return ourInstance;
    }
  }
}

package org.krypsis.gwt.store.example.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import org.krypsis.gwt.store.example.shared.Airport;

public interface AirportService extends RemoteService {
  
  public Airport save(Airport airport);

  public Airport load(String id);

  public Airport delete(String id);


  /**
   * Utility/Convenience class.
   * Use AirportService.App.getInstance() to access static instance of AirportServiceAsync
   */
  public static class App {
    private static final AirportServiceAsync ourInstance;

    static {
      ourInstance = (AirportServiceAsync) GWT.create(AirportService.class);
      ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "org.krypsis.gwt.store.example.Example/AirportService");
    }

    public static AirportServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
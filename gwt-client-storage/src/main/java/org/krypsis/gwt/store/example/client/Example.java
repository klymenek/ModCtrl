package org.krypsis.gwt.store.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import org.krypsis.gwt.store.example.client.gin.AppGinjector;
import org.krypsis.gwt.store.example.client.service.AirlineServiceAsync;
import org.krypsis.gwt.store.example.client.service.AirplaneServiceAsync;
import org.krypsis.gwt.store.example.client.service.AirportServiceAsync;

public class Example implements EntryPoint {
  private final AppGinjector ginjector = GWT.create(AppGinjector.class);
  private final AirlineServiceAsync airlineService;
  private final AirplaneServiceAsync airplaneService;
  private final AirportServiceAsync airportService;

  public Example() {
    this.airlineService = ginjector.getAirlineService();
    this.airplaneService = ginjector.getAirplaneService();
    this.airportService = ginjector.getAirportService();
  }

  public void onModuleLoad() {
    // Do something with the services
  }

  public AppGinjector getGinjector() {
    return ginjector;
  }

  public AirlineServiceAsync getAirlineService() {
    return airlineService;
  }

  public AirplaneServiceAsync getAirplaneService() {
    return airplaneService;
  }

  public AirportServiceAsync getAirportService() {
    return airportService;
  }
}

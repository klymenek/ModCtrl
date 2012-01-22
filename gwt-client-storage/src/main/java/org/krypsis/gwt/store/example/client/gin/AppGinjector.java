package org.krypsis.gwt.store.example.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.krypsis.gwt.store.example.client.service.AirlineServiceAsync;
import org.krypsis.gwt.store.example.client.service.AirplaneServiceAsync;
import org.krypsis.gwt.store.example.client.service.AirportServiceAsync;

@GinModules({AppModule.class})
public interface AppGinjector extends Ginjector {
  public AirlineServiceAsync getAirlineService();
  public AirplaneServiceAsync getAirplaneService();
  public AirportServiceAsync getAirportService();
}

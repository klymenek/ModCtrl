package org.krypsis.gwt.store.example.client.service.store;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import org.krypsis.gwt.store.client.Store;
import org.krypsis.gwt.store.client.StoreService;
import org.krypsis.gwt.store.example.client.javascript.JsAirport;
import org.krypsis.gwt.store.example.client.service.AirportServiceAsync;
import org.krypsis.gwt.store.example.client.service.Converter;
import org.krypsis.gwt.store.example.shared.Airport;

public class StoreAirportServiceAsync implements AirportServiceAsync {
  private Converter converter;
  private Store<JsAirport> airportStore;

  @Inject
  @SuppressWarnings({"unchecked"})
  public StoreAirportServiceAsync(StoreService storeService, Converter converter) {
    this.converter = converter;

    /* Request the airplane store from store service */
    this.airportStore = storeService.getStore(JsAirport.class, JsAirport.NAMESPACE);
  }

  public void load(String id, AsyncCallback<Airport> callback) {
    try {
      final JsAirport jsAirport = airportStore.get(id);
      callback.onSuccess(converter.convert(jsAirport));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void save(Airport airport, AsyncCallback<Airport> callback) {
    try {
      final JsAirport jsAirport = converter.convert(airport);
      final String id = airportStore.save(jsAirport);
      airport.setId(id);
      callback.onSuccess(airport);
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void delete(String id, AsyncCallback<Airport> callback) {
    try {
      final JsAirport jsAirport = airportStore.get(id);
      if (jsAirport != null) {
        airportStore.delete(id);
      }
      callback.onSuccess(converter.convert(jsAirport));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }
}

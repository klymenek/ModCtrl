package org.krypsis.gwt.store.example.client.service.store;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.krypsis.gwt.store.client.Matcher;
import org.krypsis.gwt.store.client.Store;
import org.krypsis.gwt.store.client.StoreService;
import org.krypsis.gwt.store.example.client.javascript.JsAirplane;
import org.krypsis.gwt.store.example.client.service.AirplaneServiceAsync;
import org.krypsis.gwt.store.example.client.service.Converter;
import org.krypsis.gwt.store.example.shared.Airplane;
import org.krypsis.gwt.store.example.shared.Airport;

import java.util.List;

@Singleton
public class StoreAirplaneServiceAsync implements AirplaneServiceAsync {
  private final Converter converter;
  private Store<JsAirplane> airplaneStore;
  
  @Inject
  @SuppressWarnings({"unchecked"})
  public StoreAirplaneServiceAsync(StoreService storeService, Converter converter) {
    this.converter = converter;

    /* Request the airplane store from store service */
    this.airplaneStore = storeService.getStore(JsAirplane.class, JsAirplane.NAMESPACE);
  }

  public void save(Airplane airplane, AsyncCallback<Airplane> callback) {
    try {
      final JsAirplane jsAirplane = converter.convert(airplane);
      final String id = airplaneStore.save(jsAirplane);
      airplane.setId(id);
      callback.onSuccess(airplane);
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void load(String id, AsyncCallback<Airplane> callback) {
    try {
      final JsAirplane jsAirplane = airplaneStore.get(id);
      callback.onSuccess(converter.convert(jsAirplane));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void delete(String id, AsyncCallback<Airplane> callback) {
    try {
      final JsAirplane jsAirplane = airplaneStore.get(id);
      if (jsAirplane != null) {
        airplaneStore.delete(jsAirplane);
      }
      callback.onSuccess(converter.convert(jsAirplane));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void findByDestinationAirport(final Airport airport, AsyncCallback<List<Airplane>> callback) {
    try {

      final List<JsAirplane> jsAirplanes = airplaneStore.findAll(new Matcher<JsAirplane>() {
        public boolean match(JsAirplane airplane) {
          return airplane.getDestinationAirportId().equals(airport.getId());
        }
      });

      callback.onSuccess(converter.convert(jsAirplanes));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void findBySourceAirport(final Airport airport, AsyncCallback<List<Airplane>> callback) {
    try {
      final List<JsAirplane> jsAirplanes = airplaneStore.findAll(new Matcher<JsAirplane>() {
        public boolean match(JsAirplane airplane) {
          return airplane.getSourceAirportId().equals(airport.getId());
        }
      });

      callback.onSuccess(converter.convert(jsAirplanes));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }
}

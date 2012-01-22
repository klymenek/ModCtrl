package org.krypsis.gwt.store.example.client.service.store;

import org.krypsis.gwt.store.example.client.service.AirlineServiceAsync;
import org.krypsis.gwt.store.example.client.service.Converter;
import org.krypsis.gwt.store.example.client.javascript.JsAirplane;
import org.krypsis.gwt.store.example.client.javascript.JsAirline;
import org.krypsis.gwt.store.example.shared.Airplane;
import org.krypsis.gwt.store.example.shared.Airline;
import org.krypsis.gwt.store.client.Store;
import org.krypsis.gwt.store.client.StoreService;
import org.krypsis.gwt.store.client.Matcher;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class StoreAirlineServiceAsync implements AirlineServiceAsync {
  private final Converter converter;
  private final Store<JsAirplane> airplaneStore;
  private final Store<JsAirline> airlineStore;

  @Inject
  @SuppressWarnings({"unchecked"})
  public StoreAirlineServiceAsync(StoreService storeService, Converter converter) {
    this.converter = converter;
    this.airplaneStore = storeService.getStore(JsAirplane.class, JsAirplane.NAMESPACE);
    this.airlineStore  = storeService.getStore(JsAirline.class,  JsAirline.NAMESPACE);
  }

  public void load(String id, AsyncCallback<Airline> callback) {
    try {
      final JsAirline jsAirline = airlineStore.get(id);
      callback.onSuccess(converter.convert(jsAirline));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void save(Airline airline, AsyncCallback<Airline> callback) {
    try {
      final JsAirline jsAirline = converter.convert(airline);
      final String id = airlineStore.save(jsAirline);
      airline.setId(id);
      callback.onSuccess(airline);
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void addAirplane(String id, Airplane airplane, AsyncCallback<Airline> callback) {
    try {
      final JsAirplane jsAirplane = airplaneStore.get(airplane.getId());
      final JsAirline jsAirline = airlineStore.get(id);

      if (jsAirplane == null || jsAirline == null) {
        throw new NullPointerException("Either airline or airplane not found. Airline = " + id + ", airplane = " + airplane);
      }
      
      jsAirplane.setAirlineId(id);
      jsAirline.addAirplane(jsAirplane);
      airplaneStore.save(jsAirplane);
      airlineStore.save(jsAirline);

      callback.onSuccess(converter.convert(jsAirline));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void getAirplanes(String id, AsyncCallback<List<Airplane>> callback) {
    try {
      final JsAirline jsAirline = airlineStore.get(id);
      final List<JsAirplane> jsAirplanes = airplaneStore.all(jsAirline.getAirplaneIds());
      callback.onSuccess(converter.convert(jsAirplanes));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void removeAirplane(String id, Airplane airplane, AsyncCallback<Airline> callback) {
    try {
      final JsAirline jsAirline = airlineStore.get(id);
      final JsAirplane jsAirplane = airplaneStore.get(airplane.getId());

      if (jsAirline == null || jsAirplane == null) {
        throw new NullPointerException("Either airline or airplane not found. Airline = " + id + ", airplane = " + airplane);
      }

      jsAirplane.setAirlineId(null);
      jsAirline.removeAirplane(jsAirplane);

      airlineStore.save(jsAirline);
      airplaneStore.save(jsAirplane);

      callback.onSuccess(converter.convert(jsAirline));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void delete(String id, AsyncCallback<Airline> callback) {
    try {
      final JsAirline jsAirline = airlineStore.get(id);
      if (jsAirline != null) {
        for (String airplaneId : jsAirline.getAirplaneIds()) {
          final JsAirplane jsAirplane = airplaneStore.get(airplaneId);
          jsAirplane.setAirlineId(null);
          airplaneStore.save(jsAirplane);
        }

        airlineStore.delete(jsAirline);
      }

      callback.onSuccess(converter.convert(jsAirline));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void findByName(final String name, AsyncCallback<Airline> callback) {
    try {
      final JsAirline first = airlineStore.findFirst(new Matcher<JsAirline>() {
        public boolean match(JsAirline instance) {
          return instance.getName().equals(name);
        }
      });
      
      callback.onSuccess(converter.convert(first));
    }
    catch (Exception e) {
      callback.onFailure(e);
    }
  }
}

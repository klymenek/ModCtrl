package org.krypsis.gwt.store.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.krypsis.gwt.store.example.shared.Airplane;
import org.krypsis.gwt.store.example.shared.Airport;

import java.util.List;

public interface AirportServiceAsync {

  public void save(Airport airport, AsyncCallback<Airport> callback);

  public void load(String id, AsyncCallback<Airport> callback);

  public void delete(String id, AsyncCallback<Airport> callback);
}
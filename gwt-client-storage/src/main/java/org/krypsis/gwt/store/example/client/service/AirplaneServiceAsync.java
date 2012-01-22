package org.krypsis.gwt.store.example.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.krypsis.gwt.store.example.shared.Airplane;
import org.krypsis.gwt.store.example.shared.Airport;

import java.util.List;

public interface AirplaneServiceAsync {

  public void save(Airplane airplane, AsyncCallback<Airplane> callback);

  public void load(String id, AsyncCallback<Airplane> callback);

  public void delete(String id, AsyncCallback<Airplane> callback);

  public void findBySourceAirport(Airport airport, AsyncCallback<List<Airplane>> callback);
  
  public void findByDestinationAirport(Airport airport, AsyncCallback<List<Airplane>> callback);
}

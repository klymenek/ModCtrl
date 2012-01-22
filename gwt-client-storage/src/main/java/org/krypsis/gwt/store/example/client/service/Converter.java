package org.krypsis.gwt.store.example.client.service;

import org.krypsis.gwt.store.example.client.javascript.JsAirplane;
import org.krypsis.gwt.store.example.client.javascript.JsAirport;
import org.krypsis.gwt.store.example.client.javascript.JsAirline;
import org.krypsis.gwt.store.example.shared.Airplane;
import org.krypsis.gwt.store.example.shared.Airport;
import org.krypsis.gwt.store.example.shared.Airline;

import java.util.List;
import java.util.ArrayList;

/**
 * This class converts the javascript object into shared objects
 * and back. Is massively used in the service / persistence layer
 */
public class Converter {

  public Airline convert(JsAirline jsAirline) {
    if (jsAirline == null) {
      return null;
    }
    final Airline airline = new Airline();
    airline.setId(jsAirline.getId());
    airline.setName(jsAirline.getName());
    airline.setAirplaneIds(jsAirline.getAirplaneIds());
    return airline;
  }

  public JsAirline convert(Airline airline) {
    if (airline == null) {
      return null;
    }
    final JsAirline jsAirline = JsAirline.build();
    jsAirline.setId(airline.getId());
    jsAirline.setName(airline.getName());
    jsAirline.setAirplaneIds(airline.getAirplaneIds());
    return jsAirline;
  }

  public JsAirport convert(Airport airport) {
    if (airport == null) {
      return null;
    }
    final JsAirport jsAirport = JsAirport.build();
    jsAirport.setId(airport.getId());
    jsAirport.setCountry(airport.getCountry());
    return jsAirport;
  }

  public Airport convert(JsAirport jsAirport) {
    if (jsAirport == null) {
      return null;
    }
    final Airport airport = new Airport();
    airport.setId(jsAirport.getId());
    airport.setCountry(jsAirport.getCountry());
    return airport;
  }

  public JsAirplane convert(Airplane airplane) {
    if (airplane == null) {
      return null;
    }
    final JsAirplane jsAirplane = JsAirplane.build();
    jsAirplane.setId(airplane.getId());
    jsAirplane.setFlightNumber(airplane.getFlightNumber());
    jsAirplane.setSourceAirportId(airplane.getSourceAirportId());
    jsAirplane.setDestinationAirportId(airplane.getDestinationAirportId());
    jsAirplane.setAirlineId(airplane.getAirlineId());
    return jsAirplane;
  }

  public Airplane convert(JsAirplane jsAirplane) {
    if (jsAirplane == null) {
      return null;
    }
    final Airplane airplane = new Airplane();
    airplane.setId(jsAirplane.getId());
    airplane.setAirlineId(jsAirplane.getAirlineId());
    airplane.setFlightNumber(jsAirplane.getFlightNumber());
    airplane.setSourceAirportId(jsAirplane.getSourceAirportId());
    airplane.setDestinationAirportId(jsAirplane.getDestinationAirportId());
    return airplane;
  }

  public List<Airplane> convert(List<JsAirplane> jsAirplanes) {
    final ArrayList<Airplane> result = new ArrayList<Airplane>();
    if (jsAirplanes.size() > 0) {
      for (JsAirplane jsAirplane : jsAirplanes) {
        result.add(convert(jsAirplane));
      }
    }
    return result;
  }
}

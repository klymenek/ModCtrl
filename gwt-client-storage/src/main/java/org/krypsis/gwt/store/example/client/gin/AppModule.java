package org.krypsis.gwt.store.example.client.gin;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.krypsis.gwt.store.client.StoreBackend;
import org.krypsis.gwt.store.client.StoreService;
import org.krypsis.gwt.store.example.client.service.AirlineServiceAsync;
import org.krypsis.gwt.store.example.client.service.AirplaneServiceAsync;
import org.krypsis.gwt.store.example.client.service.AirportServiceAsync;
import org.krypsis.gwt.store.example.client.service.Converter;
import org.krypsis.gwt.store.example.client.service.store.StoreAirlineServiceAsync;
import org.krypsis.gwt.store.example.client.service.store.StoreAirplaneServiceAsync;
import org.krypsis.gwt.store.example.client.service.store.StoreAirportServiceAsync;

public class AppModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(Converter.class).in(Singleton.class);
        bind(HandlerManager.class).to(EventBus.class).in(Singleton.class);
        bind(AirlineServiceAsync.class).to(StoreAirlineServiceAsync.class).in(Singleton.class);
        bind(AirplaneServiceAsync.class).to(StoreAirplaneServiceAsync.class).in(Singleton.class);
        bind(AirportServiceAsync.class).to(StoreAirportServiceAsync.class).in(Singleton.class);
        bind(StoreBackend.class).toProvider(LocalStoreBackendProvider.class).in(Singleton.class);
        bind(StoreService.class).toProvider(JsStoreServiceProvider.class).in(Singleton.class);
    }
}

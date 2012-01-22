package org.krypsis.gwt.store.test.client.javascript;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;
import java.util.List;
import org.krypsis.gwt.store.client.Store;
import org.krypsis.gwt.store.client.StoreService;
import org.krypsis.gwt.store.client.backend.MemoryBackend;
import org.krypsis.gwt.store.client.event.*;
import org.krypsis.gwt.store.client.javascript.JsStorable;
import org.krypsis.gwt.store.client.javascript.JsStoreService;

public class JavaScriptStoreServiceGwtTest extends GWTTestCase {

    private MemoryBackend backend;
    private StoreService<JsStorable> service;
    private HandlerManager eventBus;

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();
        backend = new MemoryBackend();
        backend.clear();
        service = new JsStoreService(backend);
        eventBus = service.getEventBus();
    }

    public void testGetStores() {
        final Store<Person> personStore1 = service.getStore(Person.class, "org.krypsis.Person");
        final Store<Person> personStore2 = service.getStore(Person.class, "org.krypsis.Person");
        assertEquals(personStore1, personStore2);
    }

    public void testEventSharingBetweenStores() {
        final Store<Person> personStore = service.getStore(Person.class, "org.krypsis.Person");
        final Store<Bus> busStore = service.getStore(Bus.class, "org.krypsis.Bus");

        final boolean[] busCalled = new boolean[]{false};
        final boolean[] personCalled = new boolean[]{false};

        eventBus.addHandler(AfterClearedEvent.TYPE, new AfterClearedEventHandler() {

            @Override
            public void onEvent(AfterClearedEvent event) {
                if (event.getStore().equals(personStore)) {
                    personCalled[0] = true;
                }
            }
        });
        eventBus.addHandler(AfterClearedEvent.TYPE, new AfterClearedEventHandler() {

            @Override
            public void onEvent(AfterClearedEvent event) {
                if (event.getStore().equals(busStore)) {
                    busCalled[0] = true;
                }
            }
        });

        personStore.deleteAll();
        assertFalse(busCalled[0]);
        assertTrue(personCalled[0]);
        personCalled[0] = false;
        busStore.deleteAll();
        assertTrue(busCalled[0]);
        assertFalse(personCalled[0]);
    }

    public void testPersonBusGlue() {
        final Store<Bus> busStore = service.getStore(Bus.class, "org.krypsis.Bus");
        final Store<Person> personStore = service.getStore(Person.class, "org.krypsis.Person");

        eventBus.addHandler(AfterSavedEvent.TYPE, new AfterSavedEventHandler<Person>() {

            @Override
            public void onEvent(AfterSavedEvent<Person> event) {
                if (event.getInstance().getBusId() != null) {
                    final Bus bus = busStore.get(event.getInstance().getBusId());
                    if (bus == null) {
                        return;
                    }

                    bus.addPerson(event.getInstance());
                    busStore.save(bus);
                }
            }
        });

        eventBus.addHandler(AfterDeletedEvent.TYPE, new AfterDeletedEventHandler<Person>() {

            @Override
            public void onEvent(AfterDeletedEvent<Person> event) {
                if (event.getInstance().getBusId() != null) {
                    final Bus bus = busStore.get(event.getInstance().getBusId());
                    if (bus == null) {
                        return;
                    }

                    bus.removePerson(event.getInstance());
                    busStore.save(bus);
                }
            }
        });

        final Bus bus = Bus.build();
        bus.setId("100");
        bus.setName("Bus 1");
        busStore.save(bus);

        final Person person = Person.build();
        person.setBusId("100");
        person.setName("Person 1");
        person.setId("200");
        personStore.save(person);

        List<String> ids = busStore.get("100").getPersonIds();
        assertEquals(1, ids.size());
        assertEquals("200", ids.get(0));

        personStore.delete(person);

        ids = busStore.get("100").getPersonIds();
        assertEquals(0, ids.size());
    }

    @Override
    public String getModuleName() {
        return "org.krypsis.gwt.store.test.StoreTest";
    }
}

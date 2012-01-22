package org.krypsis.gwt.store.test.client.javascript;

import com.google.code.gwt.storage.client.Storage;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.client.GWTTestCase;
import org.krypsis.gwt.store.client.Store;
import org.krypsis.gwt.store.client.javascript.JsStore;
import org.krypsis.gwt.store.client.backend.LocalStorageBackend;
import org.krypsis.gwt.store.client.event.*;

public class JavaScriptStorageListenerGwtTest extends GWTTestCase {

    private Store<Person> store;
    private HandlerManager eventBus;

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();
        final Storage localStorage = Storage.getLocalStorage();
        localStorage.clear();
        final LocalStorageBackend backend = new LocalStorageBackend(localStorage);
        eventBus = new HandlerManager(null);
        store = new JsStore<Person>("org.krypsis.Person", backend, eventBus);
        assertNull(localStorage.getItem("org.krypsis.Person"));
    }

    public void testInsertAndSaveListener() {
        final boolean[] called = new boolean[]{false, false, false, false};

        eventBus.addHandler(BeforeInsertedEvent.TYPE, new BeforeInsertedEventHandler() {

            @Override
            public void onEvent(BeforeInsertedEvent event) {
                assertEquals(0, store.size());
                called[0] = true;
            }
        });
        eventBus.addHandler(BeforeSavedEvent.TYPE, new BeforeSavedEventHandler() {

            @Override
            public void onEvent(BeforeSavedEvent event) {
                assertEquals(0, store.size());
                called[1] = true;
            }
        });
        eventBus.addHandler(AfterInsertedEvent.TYPE, new AfterInsertedEventHandler() {

            @Override
            public void onEvent(AfterInsertedEvent event) {
                assertEquals(1, store.size());
                called[2] = true;
            }
        });
        eventBus.addHandler(AfterSavedEvent.TYPE, new AfterSavedEventHandler() {

            @Override
            public void onEvent(AfterSavedEvent event) {
                assertEquals(1, store.size());
                called[3] = true;
            }
        });

        store.save(Person.build());

        for (boolean b : called) {
            assertTrue(b);
        }
    }

    public void testUpdateAndSaveListener() {
        final boolean[] called = new boolean[]{false, false, false, false};
        final Person person = Person.build();

        eventBus.addHandler(BeforeUpdatedEvent.TYPE, new BeforeUpdatedEventHandler() {

            @Override
            public void onEvent(BeforeUpdatedEvent event) {
                assertEquals(0, store.size());
                assertEquals(person, event.getInstance());
                called[0] = true;
            }
        });
        eventBus.addHandler(BeforeSavedEvent.TYPE, new BeforeSavedEventHandler() {

            @Override
            public void onEvent(BeforeSavedEvent event) {
                assertEquals(0, store.size());
                assertEquals(person, event.getInstance());
                called[1] = true;
            }
        });

        eventBus.addHandler(AfterUpdatedEvent.TYPE, new AfterUpdatedEventHandler() {

            @Override
            public void onEvent(AfterUpdatedEvent event) {
                assertEquals(1, store.size());
                assertEquals(person, event.getInstance());
                called[2] = true;
            }
        });
        eventBus.addHandler(AfterSavedEvent.TYPE, new AfterSavedEventHandler() {

            @Override
            public void onEvent(AfterSavedEvent event) {
                assertEquals(1, store.size());
                assertEquals(person, event.getInstance());
                called[3] = true;
            }
        });

        person.setId("1");
        store.save(person);

        for (boolean b : called) {
            assertTrue(b);
        }
    }

    public void testDeleteListener() {
        final boolean[] called = new boolean[]{false, false};
        final Person person = Person.build();

        eventBus.addHandler(BeforeDeletedEvent.TYPE, new BeforeDeletedEventHandler() {

            @Override
            public void onEvent(BeforeDeletedEvent event) {
                called[0] = true;
                assertEquals(1, store.size());
                assertEquals(person, event.getInstance());
            }
        });


        eventBus.addHandler(AfterDeletedEvent.TYPE, new AfterDeletedEventHandler<Person>() {

            @Override
            public void onEvent(AfterDeletedEvent<Person> event) {
                called[1] = true;
                assertEquals(0, store.size());
                assertEquals(person, event.getInstance());
            }
        });

        store.save(person);
        store.delete(person);

        for (boolean b : called) {
            assertTrue(b);
        }
    }

    public void testDeleteAllListener() {
        final boolean[] called = new boolean[]{false, false};

        eventBus.addHandler(BeforeClearedEvent.TYPE, new BeforeClearedEventHandler() {

            @Override
            public void onEvent(BeforeClearedEvent event) {
                assertEquals(10, store.size());
                called[0] = true;
            }
        });
        eventBus.addHandler(AfterClearedEvent.TYPE, new AfterClearedEventHandler() {

            @Override
            public void onEvent(AfterClearedEvent event) {
                assertEquals(0, store.size());
                called[1] = true;
            }
        });

        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId("" + (i + 1));
            store.save(person);
        }

        store.deleteAll();

        for (boolean b : called) {
            assertTrue(b);
        }
    }

    public void testAddAndRemoveListener() {
        final boolean[] called = new boolean[]{false};
        final AfterClearedEventHandler handler = new AfterClearedEventHandler() {

            @Override
            public void onEvent(AfterClearedEvent event) {
                called[0] = true;
            }
        };
        final HandlerRegistration registration = eventBus.addHandler(AfterClearedEvent.TYPE, handler);
        store.deleteAll();
        assertTrue(called[0]);
        called[0] = false;
        registration.removeHandler();
        store.deleteAll();
        assertFalse(called[0]);
    }

    @Override
    public String getModuleName() {
        return "org.krypsis.gwt.store.test.StoreTest";
    }
}
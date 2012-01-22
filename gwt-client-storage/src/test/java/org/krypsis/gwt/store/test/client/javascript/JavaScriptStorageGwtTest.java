package org.krypsis.gwt.store.test.client.javascript;

import com.google.code.gwt.storage.client.Storage;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.junit.client.GWTTestCase;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.krypsis.gwt.store.client.Matcher;
import org.krypsis.gwt.store.client.PropertyMatcher;
import org.krypsis.gwt.store.client.Store;
import org.krypsis.gwt.store.client.StoreService;
import org.krypsis.gwt.store.client.backend.LocalStorageBackend;
import org.krypsis.gwt.store.client.javascript.JsStorable;
import org.krypsis.gwt.store.client.javascript.JsStoreService;

public class JavaScriptStorageGwtTest extends GWTTestCase {

    private Store<Person> store;
    private Storage localStorage;

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();
        localStorage = Storage.getLocalStorage();
        localStorage.clear();
        final LocalStorageBackend backend = new LocalStorageBackend(localStorage);
        final StoreService<JsStorable> service = new JsStoreService(backend);
        store = service.getStore(Person.class, "org.krypsis.Person");
        assertNull(localStorage.getItem("org.krypsis.Person"));
    }

    public void testSequence() {
        String oldId = null;
        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            final String id = store.save(person);
            if (oldId != null) {
                assertTrue(Integer.parseInt(oldId) < Integer.parseInt(id));
            }
            oldId = id;
        }
    }

    public void testAdd() {
        final Person person = Person.build();
        person.setName("John Doe");
        final Date date = new Date();
        person.setBirthday(date);

        final Address address1 = Address.build();
        address1.setStreet("Wallstreet");
        address1.setZip("12345");
        address1.setCity("New York");
        person.addAddress(address1);

        final Address address2 = Address.build();
        address2.setStreet("Berliner Allee");
        address2.setZip("22222");
        address2.setCity("Hamburg");
        person.addAddress(address2);

        final String id = store.save(person);
        assertNotNull(id);

        final String people = localStorage.getItem(store.getKey().getName());
        assertNotNull(people);

        final JSONObject peopleObject = JSONParser.parse(people).isObject();
        assertNotNull(peopleObject);
        assertTrue(peopleObject.containsKey(id));
        final Person savedPerson = (Person) peopleObject.get(id).isObject().getJavaScriptObject();
        assertNotNull(savedPerson);

        assertEquals(id, savedPerson.getId());
        assertEquals(person.getName(), savedPerson.getName());
        assertEquals(date, savedPerson.getBirthDay());

        final JsArray<Address> addresses = savedPerson.getAddresses();
        assertEquals(2, addresses.length());

        final Address savedAddress1 = addresses.get(0);
        final Address savedAddress2 = addresses.get(1);

        assertEquals(address1.getStreet(), savedAddress1.getStreet());
        assertEquals(address1.getZip(), savedAddress1.getZip());
        assertEquals(address1.getCity(), savedAddress1.getCity());

        assertEquals(address2.getStreet(), savedAddress2.getStreet());
        assertEquals(address2.getZip(), savedAddress2.getZip());
        assertEquals(address2.getCity(), savedAddress2.getCity());
    }

    public void testGet() {
        final Person person = Person.build();
        person.setName("John");
        final Date date = new Date();
        person.setBirthday(date);
        final String id = store.save(person);

        assertNotNull(id);
        final Person savedPerson = store.get(id);
        assertEquals(id, savedPerson.getId());
        assertEquals(person.getName(), savedPerson.getName());
        assertEquals(date, savedPerson.getBirthDay());
    }

    public void testAll() {
        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId(String.valueOf(i + 1));
            person.setName("john" + i);
            person.setBirthday(new Date(i + 1));
            store.save(person);
        }

        final List<Person> people = store.all();
        assertEquals(10, people.size());
        for (int i = 0; i < people.size(); i++) {
            final Person person = people.get(i);
            assertEquals(String.valueOf(i + 1), person.getId());
            assertEquals("john" + i, person.getName());
            assertEquals(new Date(i + 1), person.getBirthDay());
        }
    }

    public void testAllIds() {
        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId(String.valueOf(i + 1));
            person.setName("john" + i);
            person.setBirthday(new Date(i + 1));
            store.save(person);
        }

        final List<Person> people = store.all(Arrays.asList("2", "3", "4", "5", "6"));
        assertEquals(5, people.size());
        for (int i = 2; i <= 6; i++) {
            final Person person = people.get(i - 2);
            assertEquals(String.valueOf(i), person.getId());
            assertEquals("john" + (i - 1), person.getName());
            assertEquals(new Date(i), person.getBirthDay());
        }
    }

    public void testFindFirst() {
        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId(String.valueOf(i + 1));
            person.setName("john" + i);
            person.setBirthday(new Date(i + 1));
            store.save(person);
        }

        /*
         * Find first Person where id is > 3
         */
        Person first = store.findFirst(new Matcher<Person>() {

            @Override
            public boolean match(Person person) {
                final int id = Integer.parseInt(person.getId());
                return id > 3;
            }
        });

        assertEquals("4", first.getId());
        assertEquals("john3", first.getName());
        assertEquals(new Date(4), first.getBirthDay());

        /*
         * Find first Person where id is > 3
         */
        first = store.findFirst(new Matcher<Person>() {

            @Override
            public boolean match(Person person) {
                return person.getName().equals("john9");
            }
        });

        assertEquals("10", first.getId());
        assertEquals("john9", first.getName());
        assertEquals(new Date(10), first.getBirthDay());

        /*
         * Find birthday of john9
         */
        final Date birthDay = store.findFirst(new PropertyMatcher<Person, Date>() {

            @Override
            public Date getValueOf(Person instance) {
                return instance.getBirthDay();
            }

            @Override
            public boolean match(Person instance) {
                return instance.getName().equals("john9");
            }
        });
        assertEquals(new Date(10), birthDay);
    }

    public void testFindAll() {
        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId(String.valueOf(i + 1));
            person.setName("john" + i);
            person.setBirthday(new Date(i + 1));
            store.save(person);
        }

        /*
         * Find first Person where id is > 3
         */
        final List<Person> people = store.findAll(new Matcher<Person>() {

            @Override
            public boolean match(Person person) {
                final int id = Integer.parseInt(person.getId());
                return id >= 3 && id <= 8;
            }
        });

        assertEquals(6, people.size());
        for (int i = 3; i <= 8; i++) {
            final Person person = people.get(i - 3);
            assertEquals(String.valueOf(i), person.getId());
            assertEquals("john" + (i - 1), person.getName());
            assertEquals(new Date(i), person.getBirthDay());
        }

        assertEquals(0, store.findAll(new Matcher<Person>() {

            @Override
            public boolean match(Person instance) {
                return false;
            }
        }).size());

        /*
         * Find all ids >= 5
         */
        List<String> ids = store.findAll(new PropertyMatcher<Person, String>() {

            @Override
            public String getValueOf(Person instance) {
                return instance.getId();
            }

            @Override
            public boolean match(Person instance) {
                return Integer.parseInt(instance.getId()) > 5;
            }
        });
        assertEquals(5, ids.size());
        for (int i = 6; i <= 10; i++) {
            assertTrue(ids.contains(String.valueOf(i)));
        }
    }

    public void testDelete() {
        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId(String.valueOf(i + 1));
            person.setName("john" + i);
            person.setBirthday(new Date(i + 1));
            store.save(person);
        }

        assertEquals(10, store.size());
        assertNotNull(store.get("8"));

        store.delete("8");
        assertEquals(9, store.size());
        assertNull(store.get("8"));

        store.delete(store.get("6"));
        assertEquals(8, store.size());
        assertNull(store.get("6"));
    }

    public void testSize() {
        assertEquals(0, store.size());

        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId(String.valueOf(i + 1));
            person.setName("john" + i);
            person.setBirthday(new Date(i + 1));
            store.save(person);
        }

        assertEquals(10, store.size());
    }

    public void testDeleteAll() {
        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId(String.valueOf(i + 1));
            person.setName("john" + i);
            person.setBirthday(new Date(i + 1));
            store.save(person);
        }

        assertEquals(10, store.size());
        store.deleteAll();
        assertEquals(0, store.size());
    }

    public void testDeleteAllByIds() {
        for (int i = 0; i < 10; i++) {
            final Person person = Person.build();
            person.setId(String.valueOf(i + 1));
            person.setName("john" + i);
            person.setBirthday(new Date(i + 1));
            store.save(person);
        }

        assertEquals(10, store.size());
        store.deleteAll(Arrays.asList("1", "2", "3"));
        assertNull(store.get("1"));
        assertNull(store.get("2"));
        assertNull(store.get("3"));
        assertEquals(7, store.size());
    }

    @Override
    public String getModuleName() {
        return "org.krypsis.gwt.store.test.StoreTest";
    }
}

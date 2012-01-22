/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client.javascript;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import org.krypsis.gwt.store.client.*;
import org.krypsis.gwt.store.client.event.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsStore<E extends JsStorable> implements Store<E> {
  private static final String SEQUENCE = "$SEQ";
  private final StoreBackend backend;
  private final StoreKey key;
  private final HandlerManager eventBus;

  public JsStore(String name, StoreBackend backend) {
    this(name, backend, null);
  }

  public JsStore(String name, StoreBackend backend, HandlerManager eventBus) {
    this.key = new StoreKey(name);
    this.backend = backend;
    this.eventBus = eventBus;
  }

  public String save(E instance) {
    return save(instance, true);
  }

  public String save(E instance, boolean fireEvents) {
    try {
      final JSONObject object = loadObject();

      boolean update = true;
      if (instance.getId() == null) {
        final String id = nextId();
        instance.setId(id);
        update = false;
      }

      if (update) {
        fireEvent(new BeforeUpdatedEvent<E>(this, instance), fireEvents);
      }
      else {
        fireEvent(new BeforeInsertedEvent<E>(this, instance), fireEvents);
      }
      fireEvent(new BeforeSavedEvent<E>(this, instance), fireEvents);

      object.put(instance.getId(), new JSONObject(instance));
      saveObject(object);

      if (update) {
        fireEvent(new AfterUpdatedEvent<E>(this, instance), fireEvents);
      }
      else {
        fireEvent(new AfterInsertedEvent<E>(this, instance), fireEvents);
      }
      fireEvent(new AfterSavedEvent<E>(this, instance), fireEvents);

      return instance.getId();
    }
    catch (Exception e) {
      throw new StoreException("Could not add instance into store", e);
    }
  }

  public E get(String id) {
    try {
      final JSONObject object = loadObject();
      if (!object.containsKey(id)) {
        return null;
      }
      //noinspection unchecked
      return (E) object.get(id).isObject().getJavaScriptObject();
    }
    catch (Exception e) {
      throw new StoreException("Could not load instance. Key = " + key + ", id = " + id, e);
    }
  }

  public List<E> all() {
    try {
      final JSONObject object = loadObject();
      return all(object.keySet());
    }
    catch (Exception e) {
      throw new StoreException("Could not return instances");
    }
  }

  public List<E> all(Collection<String> ids) {
    try {
      final List<E> result = new ArrayList<E>();
      for (String key : ids) {
        final E instance = get(key);
        if (instance == null) {
          continue;
        }
        
        result.add(instance);
      }
      return result;
    }
    catch (Exception e) {
      throw new StoreException("Could not load the instance with ids = " + ids, e);
    }
  }

  public int size() {
    return loadObject().size();
  }

  public E findFirst(Matcher<E> matcher) {
    final JSONObject object = loadObject();
    for (String key : object.keySet()) {
      final E instance = get(key);
      if (matcher.match(instance)) {
        return instance;
      }
    }
    return null;
  }

  public <T> T findFirst(PropertyMatcher<E, T> matcher) {
    final JSONObject object = loadObject();
    for (String key : object.keySet()) {
      final E instance = get(key);
      if (matcher.match(instance)) {
        return matcher.getValueOf(instance);
      }
    }
    return null;
  }

  public List<E> findAll(Matcher<E> matcher) {
    final List<E> result = new ArrayList<E>();
    for (E e : all()) {
      if (matcher.match(e)) {
        result.add(e);
      }
    }
    return result;
  }

  public <T> List<T> findAll(PropertyMatcher<E, T> matcher) {
    final List<T> result = new ArrayList<T>();
    for (E e : all()) {
      if (matcher.match(e)) {
        result.add(matcher.getValueOf(e));
      }
    }
    return result;
  }

  public void delete(E instance) {
    delete(instance, true);
  }

  public void delete(E instance, boolean fireEvents) {
    final String id = instance.getId();
    try {
      final JSONObject object = loadObject();
      if (!object.containsKey(id)) {
        throw new StoreException("Could not delete instance with id = " + id + ". It does not exists");
      }
      fireEvent(new BeforeDeletedEvent<E>(this, instance), fireEvents);
      object.put(id, null);
      saveObject(object);
      fireEvent(new AfterDeletedEvent<E>(this, instance), fireEvents);
    }
    catch (Exception e) {
      throw new StoreException("Could not delete instance with id = " + id, e);
    }
  }

  public void delete(String id) {
    delete(get(id));
  }
  
  public void deleteAll(List<String> ids) {
    deleteAll(ids, true);
  }

  @SuppressWarnings({"unchecked"})
  public void deleteAll(List<String> ids, boolean fireEvents) {
    final JSONObject object = loadObject();
    final List<E> deleted = new ArrayList<E>();
    for (String id : ids) {
      if (!object.containsKey(id)) {
        continue;
      }
      final E e = (E) object.get(id).isObject().getJavaScriptObject();
      deleted.add(e);
      fireEvent(new BeforeDeletedEvent<E>(this, e), fireEvents);
      object.put(id, null);
    }
    saveObject(object);

    for (E e : deleted) {
      fireEvent(new AfterDeletedEvent<E>(this, e), fireEvents);
    }
  }

  public void deleteAll() {
    deleteAll(true);
  }

  public void deleteAll(boolean fireEvents) {
    fireEvent(new BeforeClearedEvent(this), fireEvents);
    backend.remove(getKey().getName());
    fireEvent(new AfterClearedEvent(this), fireEvents);
  }

  /* GETTER, SETTER and private methods */

  public StoreKey getKey() {
    return key;
  }

  public StoreBackend getBackend() {
    return backend;
  }

  /**
   * Loads the object that contains all instances.
   *
   * @return The JSON Object that contains all saved instance
   */
  private JSONObject loadObject() {
    final String data = backend.get(key.getName());

    JSONObject object;
    if (data == null) {
      object = new JSONObject();
    }
    else {
      final JSONValue jsonValue = JSONParser.parse(data);
      object = jsonValue.isObject();
    }

    if (object == null) {
      throw new IllegalArgumentException("Database is broken. Array could not been deserialized");
    }

    return object;
  }

  /**
   * Loads the sequence from the backend or creates a new one.
   *
   * @return The sequence
   */
  private JSONString loadSequence() {
    final String data = backend.get(key.getName() + SEQUENCE);

    JSONString jsonString;
    if (data == null) {
      jsonString = new JSONString("1");
    }
    else {
      final JSONValue jsonValue = JSONParser.parse(data);
      jsonString = jsonValue.isString();
    }

    if (jsonString == null) {
      throw new IllegalArgumentException("Database is broken. Array could not been deserialized");
    }

    return jsonString;
  }

  /**
   * Saves the sequence
   *
   * @param sequence The sequence for the instances of this store
   */
  public void saveSequence(JSONString sequence) {
    backend.put(key.getName() + SEQUENCE, sequence.toString());
  }

  /**
   * Saves the object to the backend
   *
   * @param object The object to save
   */
  private void saveObject(JSONObject object) {
    backend.put(key.getName(), object.toString());
  }

  /**
   * @return The next id for a new instance
   */
  private String nextId() {
    final JSONString jsonString = loadSequence();
    final String id = jsonString.stringValue();
    saveSequence(new JSONString(String.valueOf(Integer.parseInt(id) + 1)));
    return id;
  }

  /**
   * Fires a store event, if event bus is given. Otherwise
   * nothing happens
   *
   * @param event The event to dispatch
   * @param reallyFire Should the event really be fired?
   */
  private void fireEvent(GwtEvent<?> event, boolean reallyFire) {
    if (eventBus != null && event != null && reallyFire) {
      eventBus.fireEvent(event);
    }
  }
}

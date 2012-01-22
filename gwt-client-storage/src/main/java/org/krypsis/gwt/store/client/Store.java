/*
 * Copyright (c) 2010 Sergej Soller (kontakt@5ws.de)
 *
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package org.krypsis.gwt.store.client;

import java.util.Collection;
import java.util.List;

public interface Store<E> {

  /**
   * Adds a new instance to the storage and fires
   * all events
   *
   * @param instance The instance to save
   * @return The identifier
   */
  public String save(E instance);

  /**
   * Saves the given instance and fires events if the given param is true.
   * Otherwiese no events will be fired.
   *
   * @param instance The instance to save
   * @param fireEvents True to fire events, otherwise false.
   * @return The Unique id.
   */
  public String save(E instance, boolean fireEvents);

  /**
   * Load an instance with the given identifier
   *
   * @param id The id of the instance
   * @return The instance or null, of no instance found
   */
  public E get(String id);

  /**
   * Returns all stored instances
   *
   * @return All instances
   */
  public List<E> all();

  /**
   * Returns all instances with the given ids.
   *
   * @param ids A list of ids
   * @return A list with the instances
   */
  public List<E> all(Collection<String> ids);

  /**
   * @return The size of all elements
   */
  public int size();

  /**
   * Finds the first instance that matches the given criteria
   *
   * @param matcher The criteria that should be matched
   * @return The instance or null, if none instance matches the criteria
   */
  public E findFirst(Matcher<E> matcher);

  /**
   * Find the first instance that matches the given criteria
   * and return a particular property value
   *
   * @param matcher The property matcher
   * @param <T> The type of the return value
   * @return The value of a property
   */
  public <T> T findFirst(PropertyMatcher<E, T> matcher);

  /**
   * Returns all instances that matches the given criteria
   *
   * @param matcher The criteria that must be matched by the instances
   * @return A list with all instances or an empty list
   */
  public List<E> findAll(Matcher<E> matcher);

  /**
   * Returns all property values of instances matches the given criteria.
   *
   * @param matcher The matcher
   * @param <T> The return type of the value
   * @return A list of property values
   */
  public <T> List<T> findAll(PropertyMatcher<E, T> matcher);

  /**
   * Deletes an instance from database
   *
   * @param instance The instance to delete
   */
  public void delete(E instance);

  /**
   * Deletes the given instance and fires all events if
   * the second param is set to true.
   * Otherwise no events will be fired.
   *
   * @param instance The instance to delete
   * @param fireEvents True if events should be fired, otherwise false
   */
  public void delete(E instance, boolean fireEvents);

  /**
   * Deletes the instance with the given id
   *
   * @param id The id of the instance
   */
  public void delete(String id);

  /**
   * Delete all instances with the given ids.
   *
   * @param ids The ids of the instance
   */
  public void deleteAll(List<String> ids);

  /**
   * Deletes all instances with the given ids and fires all events if
   * the second param is set to true. Otherwise no events will be fired.
   *
   * @param ids The ids of the instances to delete
   * @param fireEvents True for fire events, false for not fireing events
   */
  public void deleteAll(List<String> ids, boolean fireEvents);

  /**
   * Deletes all instance from storage
   */
  public void deleteAll();

  /**
   * Deletes all instances from store and fires events if the given
   * param is set to true. Otherwise no events will be fired.
   *
   * @param fireEvents True if the events should be fired, false otherwise.
   */
  public void deleteAll(boolean fireEvents);

  /**
   * @return The storage key for this instance
   */
  public StoreKey getKey();

  /**
   * @return A reference to the local store
   */
  public StoreBackend getBackend();
}

package org.krypsis.gwt.store.example.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;

public abstract class SuccessCallback<T> implements AsyncCallback<T> {
  public void onFailure(Throwable caught) {
    GWT.log("Error: ", caught);
  }
}

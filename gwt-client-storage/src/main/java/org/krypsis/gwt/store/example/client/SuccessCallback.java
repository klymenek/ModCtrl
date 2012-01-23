package org.krypsis.gwt.store.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class SuccessCallback<T> implements AsyncCallback<T> {

    @Override
    public void onFailure(Throwable caught) {
        GWT.log("Error: ", caught);
    }
}

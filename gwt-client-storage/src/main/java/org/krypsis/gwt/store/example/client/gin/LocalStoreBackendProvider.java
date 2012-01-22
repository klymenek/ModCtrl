package org.krypsis.gwt.store.example.client.gin;

import com.google.code.gwt.storage.client.Storage;
import com.google.inject.Provider;
import org.krypsis.gwt.store.client.StoreBackend;
import org.krypsis.gwt.store.client.backend.LocalStorageBackend;

public class LocalStoreBackendProvider implements Provider<StoreBackend> {

    @Override
    public StoreBackend get() {
        return new LocalStorageBackend(Storage.getLocalStorage());
    }
}

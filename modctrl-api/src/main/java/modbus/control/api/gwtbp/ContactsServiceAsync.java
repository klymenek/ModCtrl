package modbus.control.api.gwtbp;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContactsServiceAsync {

    public <T extends Response> void execute(Action<T> action, AsyncCallback<T> callback);
}

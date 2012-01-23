package modbus.control.api.gwtbp;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ContactsService extends RemoteService {

    public <T extends Response> T execute(Action<T> action);
}

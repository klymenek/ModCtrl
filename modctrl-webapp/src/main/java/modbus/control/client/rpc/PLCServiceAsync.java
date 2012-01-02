package modbus.control.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import modbus.control.shared.ProcessVar;

/**
 * The async counterpart of <code>DIDOService</code>.
 */
public interface PLCServiceAsync {

    void writeCoil(Boolean state, ProcessVar var, AsyncCallback<Integer> callback);

    void readCoil(ProcessVar var, AsyncCallback<Boolean> callback);
}

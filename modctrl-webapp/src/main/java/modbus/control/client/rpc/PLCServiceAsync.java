package modbus.control.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import modbus.control.shared.ProcessVarJso;

/**
 * The async counterpart of <code>DIDOService</code>.
 */
public interface PLCServiceAsync {

    void writeCoil(Boolean state, ProcessVarJso var, AsyncCallback<Integer> callback);

    void readCoil(ProcessVarJso var, AsyncCallback<Boolean> callback);
}

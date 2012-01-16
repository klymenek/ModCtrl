package modbus.control.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import modbus.control.shared.ProcessVarJso;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("plc")
public interface PLCService extends RemoteService {

    Integer writeCoil(Boolean state, ProcessVarJso var);

    Boolean readCoil(ProcessVarJso var);
}

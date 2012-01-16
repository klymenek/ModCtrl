package modbus.control.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import modbus.control.api.model.ProcessVar;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("plc")
public interface PLCService extends RemoteService {

    Integer writeCoil(Boolean state, ProcessVar var);

    Boolean readCoil(ProcessVar var);
}

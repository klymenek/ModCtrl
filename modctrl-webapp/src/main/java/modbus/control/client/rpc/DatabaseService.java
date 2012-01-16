package modbus.control.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import modbus.control.shared.CategoryJso;
import modbus.control.shared.ProcessVarJso;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("database")
public interface DatabaseService extends RemoteService {

    List<CategoryJso> getCategory();

    List<ProcessVarJso> getVarsByCategory(CategoryJso CategoryJso);
}

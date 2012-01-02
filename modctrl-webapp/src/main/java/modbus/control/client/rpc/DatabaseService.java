package modbus.control.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import modbus.control.shared.ProcessVar;
import modbus.control.shared.Category;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("database")
public interface DatabaseService extends RemoteService {

    List<Category> getCategorys();

    List<ProcessVar> getVarsByCategory(Category category);
}

package modbus.control.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import modbus.control.api.model.Category;
import modbus.control.api.model.ProcessVar;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("database")
public interface DatabaseService extends RemoteService {

    List<Category> getCategory();

    List<ProcessVar> getVarsByCategory(Category category);
}

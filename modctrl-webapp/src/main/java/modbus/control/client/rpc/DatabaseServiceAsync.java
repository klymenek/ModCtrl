package modbus.control.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import modbus.control.api.model.Category;
import modbus.control.api.model.ProcessVar;

/**
 * The async counterpart of <code>DIDOService</code>.
 */
public interface DatabaseServiceAsync {

    void getCategory(AsyncCallback<List<Category>> callback);

    void getVarsByCategory(Category category, AsyncCallback<List<ProcessVar>> callback);
}

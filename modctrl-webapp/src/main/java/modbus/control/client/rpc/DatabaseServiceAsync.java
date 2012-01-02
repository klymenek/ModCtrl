package modbus.control.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import modbus.control.shared.ProcessVar;
import modbus.control.shared.Category;

/**
 * The async counterpart of <code>DIDOService</code>.
 */
public interface DatabaseServiceAsync {

    void getCategorys(AsyncCallback<List<Category>> callback);

    void getVarsByCategory(Category category, AsyncCallback<List<ProcessVar>> callback);
}

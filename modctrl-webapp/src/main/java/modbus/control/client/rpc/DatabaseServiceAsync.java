package modbus.control.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import modbus.control.shared.CategoryJso;
import modbus.control.shared.ProcessVarJso;

/**
 * The async counterpart of <code>DIDOService</code>.
 */
public interface DatabaseServiceAsync {

    void getCategory(AsyncCallback<List<CategoryJso>> callback);

    void getVarsByCategory(CategoryJso category, AsyncCallback<List<ProcessVarJso>> callback);
}

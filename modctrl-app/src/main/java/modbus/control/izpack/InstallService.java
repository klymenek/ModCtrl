package modbus.control.izpack;

import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.installer.PanelAction;
import com.izforge.izpack.installer.PanelActionConfiguration;
import com.izforge.izpack.util.AbstractUIHandler;
import modbus.control.util.ServiceUtil;

/**
 *
 * @author ag
 */
public class InstallService implements PanelAction {

    @Override
    public void executeAction(AutomatedInstallData adata, AbstractUIHandler handler) {
        ServiceUtil.serviceAction(ServiceUtil.ACTION.INSTALL);
    }

    @Override
    public void initialize(PanelActionConfiguration configuration) {
        //do nothing
    }    
}

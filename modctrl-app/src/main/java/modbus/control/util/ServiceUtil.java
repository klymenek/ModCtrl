package modbus.control.util;

import java.util.HashMap;
import java.util.Map;
import modbus.control.service.WrappedServiceModCtrl;
import org.rzo.yajsw.wrapper.WrappedProcess;
import org.rzo.yajsw.wrapper.WrappedProcessFactory;

/**
 *
 * @author ag
 */
public class ServiceUtil {

    public enum ACTION {

        INSTALL, REMOVE, START
    }
    
    public static void main(String[] args) {
        serviceAction(ACTION.INSTALL);
    }

    public static void serviceAction(ACTION action) {                
        // global configuration
        System.setProperty("wrapper.config", "C:\\ModbusControl\\conf\\wrapper.conf");

        switch (action) {
            case INSTALL:                
                WrappedServiceModCtrl ws = new WrappedServiceModCtrl();
                ws.init(); // read in configuration
                ws.install(); // start the service

                break;

            case START:
                Map configuration = new HashMap();
                WrappedProcess w = (WrappedProcess) WrappedProcessFactory.createProcess(configuration, true);
                // initialiase the process
                w.init();
                // start the process
                w.start();
                w.waitFor(50000);
                // stop the process
                w.stop();

                break;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modbus.control.service;

import java.util.HashMap;
import java.util.Map;
import org.rzo.yajsw.app.WrapperMainServiceWin;
import org.rzo.yajsw.wrapper.WrappedProcess;
import org.rzo.yajsw.wrapper.WrappedProcessFactory;
import org.rzo.yajsw.wrapper.WrappedService;

/**
 *
 * @author ag
 */
public class ServiceUtil {

    enum ACTION {

        INSTALL, REMOVE, START
    }

    public static void main(String[] args) {
        ACTION a = ACTION.INSTALL;

        // global configuration
        System.setProperty("wrapper.config", "D:\\dev\\ModCtrl\\modctrl-app\\target\\staging\\ModbusControl\\conf\\wrapper.conf");

        switch (a) {
            case INSTALL:
                
                
                WrappedService ws = new WrappedService();
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

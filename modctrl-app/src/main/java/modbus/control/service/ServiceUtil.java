/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modbus.control.service;

import java.util.HashMap;
import java.util.Map;
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
                ws.init();    // read in configuration
                ws.install(); // start the service

//                System.setProperty("wrapper.config", "D:\\dev\\ModCtrl\\modctrl-app\\src\\main\\resources\\wrapper.conf"); //System.getProperty("user.dir") + "\\src\\main\\resources\\wrapper.conf");
//                System.setProperty("wrapper.java.app.mainclass", "modbus.control.EmbeddedJetty"); // system properties overwrite properties in conf file.
//                System.setProperty("wrapper.tray", "false");
//                System.setProperty("wrapper.working.dir", "D:\\dev\\ModCtrl\\modctrl-app\\target\\dependency");
//
//                // set home dir of the service
//                OperatingSystem.instance().setWorkingDir(System.getProperty("user.dir"));
//
//                service = new WrapperMainServiceWin();
//                service.setServiceName("modctrl");
//
//                service.install("Modbus Control", "control modbus PLC", null, "NT-AUTORITÄT\\System", "", true);

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
//    @Override
//    public boolean install(String displayName, String description, String[] dependencies, String account, String password, boolean delayedAutostart) {
//        File prjFolder = new File(System.getProperty("user.dir") + "\\target\\dependency");
//        if (!prjFolder.exists()) {
//            prjFolder = new File(System.getProperty("user.dir") + "\\..\\lib");
//            if (!prjFolder.exists()) {
//                logger.error("NO LIBRARYS FOUND");
//                return false;
//            }
//        }
//
//        List<String> depFiles = new ArrayList<String>();
//        for (File dependencyFile : prjFolder.listFiles()) {
//
//            if (dependencyFile.isFile() && dependencyFile.getName().endsWith("jar")) {
//                depFiles.add(dependencyFile.getAbsolutePath());
//            }
//        }
//        dependencies = depFiles.toArray(new String[0]);
//
//        return super.install(displayName, description, dependencies, account, password, delayedAutostart);
//    }
}

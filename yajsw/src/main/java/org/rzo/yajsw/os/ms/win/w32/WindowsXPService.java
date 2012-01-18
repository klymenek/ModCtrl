package org.rzo.yajsw.os.ms.win.w32;

import java.util.HashMap;
import java.util.Map;

import jnacontrib.jna.Advapi32.ENUM_SERVICE_STATUS_PROCESS;
import jnacontrib.win32.Win32Service;

import org.rzo.yajsw.os.AbstractService;
import org.rzo.yajsw.os.Service;
import org.rzo.yajsw.os.ServiceInfo;

public class WindowsXPService extends AbstractService {

    class MyWin32Service extends Win32Service {

        public MyWin32Service(String name) {
            super(name);
        }

        @Override
        public void onStart() {
        }

        @Override
        public void onStop() {
        }

        @Override
        public void log(String txt) {
            System.out.println(txt);
        }
    }
    MyWin32Service _service;

    @Override
    public boolean install() {
        String command = "";
        for (int i = 0; i < _command.length; i++) {
            if (_command[i].startsWith("\"")) {
                command += _command[i] + " ";
            } else {
                command += '"' + _command[i] + "\" ";
            }
        }

        return _service.install(_displayName, _description, _dependencies, _account, _password, command, _startType, _interactive);
    }

    public boolean start() {
        return _service.start();
    }

    public boolean stop() {
        try {
            return _service.stop();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // TODO add further data from Info
    protected static Service getService(String name) {
        WindowsXPService result = new WindowsXPService();
        result.setName(name);
        result.init();
        return result;
    }

    public void init() {
        if (_service == null) {
            _service = new MyWin32Service(_name);
        }
        if (_config != null && _config.getBoolean("wrapper.ntservice.interactive", false)) {
            _interactive = true;
        }
    }

    public boolean uninstall() {
        return _service.uninstall();
    }

    public int state() {
        if (_service == null) {
            return STATE_UNKNOWN;
        }
        return _service.state();

    }

    public static Map<String, ServiceInfo> getServiceList() {
        Map<String, ServiceInfo> result = new HashMap<String, ServiceInfo>();
        Map<String, ENUM_SERVICE_STATUS_PROCESS> services = Win32Service.enumerateServices(null);
        for (String name : services.keySet()) {
            result.put(name, getServiceInfo(name));
        }
        return result;
    }

    public static ServiceInfo getServiceInfo(String name) {
        return Win32Service.serviceInfo(name);
    }

    public boolean requestElevation() {
        if (_service != null) {
            return _service.requestElevation();
        }
        return false;
    }
}

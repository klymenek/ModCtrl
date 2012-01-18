package modbus.control.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.rzo.yajsw.script.ScriptFactory;
import org.rzo.yajsw.wrapper.WrappedProcess;

public class Utils {

    WrappedProcess _process;
    WrappedServiceModCtrl _service;

    public Utils(WrappedProcess process) {
        _process = process;
    }

    public Utils(WrappedServiceModCtrl service) {
        _service = service;
    }

    public String inquireCLI(String message) throws IOException {
        System.out.print(message + ":");
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    public String inquireTrayIcon(String message) throws InterruptedException {
        String result = null;
        if (_process == null) {
            System.out.println("ERROR in inquireTrayIcon: process == null");
            return null;
        }
        while (result == null) {
            result = _process.getTrayIcon().inquire(message);
            if (result == null) {
                Thread.sleep(2000);
            }
        }
        return result;
    }

    public Object executeScript(String file) {
        return ScriptFactory.createScript(file, null, null, (List) null, null, 0).execute();
    }
}

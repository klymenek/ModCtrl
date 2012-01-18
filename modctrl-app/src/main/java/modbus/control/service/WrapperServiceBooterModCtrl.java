package modbus.control.service;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

public class WrapperServiceBooterModCtrl {

    public static void main(String[] args) {
        if (System.getProperty("java.io.tmpdir") != null) {
            File tmp = new File(System.getProperty("java.io.tmpdir"));
            if (!tmp.exists()) {
                tmp.mkdirs();
            }
        }

        URLClassLoader cl = WrapperLoaderModCtrl.getModCtrlClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        String osName = System.getProperty("os.name");
        String clazz = null;
        if (osName.toLowerCase().startsWith("windows")) {
            clazz = "modbus.control.service.WrapperMainServiceWinModCtrl";
        } else {
            clazz = "org.rzo.yajsw.app.WrapperMainServiceUnix";
        }
        try {
            Class cls = Class.forName(clazz, true, cl);
            Method mainMethod = cls.getDeclaredMethod("main", new Class[]{String[].class});
            mainMethod.invoke(null, new Object[]{args});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

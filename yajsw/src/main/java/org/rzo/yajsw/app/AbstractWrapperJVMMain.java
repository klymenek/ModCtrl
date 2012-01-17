package org.rzo.yajsw.app;

import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class AbstractWrapperJVMMain {

    /**
     * The WRAPPE r_ manager.
     */
    public static WrapperManager WRAPPER_MANAGER;
    public static Throwable exception = null;

    protected static void postExecute() {
        int exitCode;
        if (exception == null) {
            exitCode = WRAPPER_MANAGER.getExitOnMainTerminate();
        } else {
            exitCode = WRAPPER_MANAGER.getExitOnException();
        }
        if (exitCode >= 0) {
            System.exit(exitCode);
        }
    }

    protected static void preExecute(String[] args) {
        final String[] finalArgs = args;
        WRAPPER_MANAGER = (WrapperManager) AccessController.doPrivileged(new PrivilegedAction<Object>() {

            public Object run() {
                return WrapperManagerProxy.getWrapperManager(finalArgs);
            }
        });
    }
}

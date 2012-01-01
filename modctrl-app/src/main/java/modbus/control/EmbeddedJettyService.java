package modbus.control;

import java.io.File;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

public class EmbeddedJettyService implements WrapperListener {

    @Override
    public Integer start(String[] arg0) {
        try {
            // Create an embedded Jetty server on port 8080
            Server server = new Server(8080);

            // Create a handler for processing our GWT app
            WebAppContext handler = new WebAppContext();
            handler.setContextPath("/");

            String prjFolder = System.getProperty("user.dir") + "\\..\\modctrl-webapp\\target";

            File warFile = new File(prjFolder.toString());
            File[] listFiles = warFile.listFiles();
            for (File file : listFiles) {
                if (file.isFile() && file.getName().endsWith(".war")) {
                    handler.setWar(file.getAbsolutePath());
                }
            }

            if (handler.getWar() != null && !handler.getWar().isEmpty()) {
                // Add it to the server
                server.setHandler(handler);

                // Other misc. options
                server.setThreadPool(new QueuedThreadPool(20));

                // And start it up
                server.start();
                server.join();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    @Override
    public int stop(int exitCode) {

        return 0;
    }

    @Override
    public void controlEvent(int event) {
        if (WrapperManager.isControlledByNativeWrapper()) {
            // The Wrapper will take care of this event
        } else {
            // We are not being controlled by the Wrapper, so
            // handle the event ourselves.
            if ((event == WrapperManager.WRAPPER_CTRL_C_EVENT) || (event == WrapperManager.WRAPPER_CTRL_CLOSE_EVENT)
                    || (event == WrapperManager.WRAPPER_CTRL_SHUTDOWN_EVENT)) {
                WrapperManager.stop(0);
            }
        }
    }

    /*---------------------------------------------------------------
     * Main Method
     *-------------------------------------------------------------*/
    public static void main(String[] args) {
        // Start the application. If the JVM was launched from the native
        // Wrapper then the application will wait for the native Wrapper to
        // call the application's start method. Otherwise the start method
        // will be called immediately.
        WrapperManager.start(new EmbeddedJettyService(), args);
    }
}

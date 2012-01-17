package modbus.control;

import java.io.File;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbeddedJetty {
    
    private static final Logger logger = Logger.getLogger(EmbeddedJetty.class);

    public static void main(String[] args) {
        
        try {
            // Create an embedded Jetty server on port 8080
            Server server = new Server(8888);

            // Create a handler for processing our GWT app
            WebAppContext handler = new WebAppContext();
            handler.setContextPath("/");

            String prjFolder = System.getProperty("user.dir") + "\\lib";    
            
            logger.info("WAR FOLDER RUNTIME - " + prjFolder);
            
            File warFile = new File(prjFolder.toString());
            File[] listFiles = warFile.listFiles();
            for (File file : listFiles) {
                if (file.isFile() && file.getName().endsWith(".war")) {
                    handler.setWar(file.getAbsolutePath());
                }
            }

            if (handler.getWar() == null || handler.getWar().isEmpty()) {
                prjFolder = System.getProperty("user.dir") + "\\..\\modctrl-webapp\\target";
                
                logger.info("WAR FOLDER DEBUG - " + prjFolder);

                warFile = new File(prjFolder.toString());
                listFiles = warFile.listFiles();
                for (File file : listFiles) {
                    if (file.isFile() && file.getName().endsWith(".war")) {
                        handler.setWar(file.getAbsolutePath());
                    }
                }
            }

            // Add it to the server
            server.setHandler(handler);

            // Other misc. options
            server.setThreadPool(new QueuedThreadPool(20));

            // And start it up
            server.start();
            server.join();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modbus.control;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * @author ag
 */
public class JettyEmbedded {

    public static void main(String[] args) {
        try {
            // Create an embedded Jetty server on port 8080
            Server server = new Server(8080);

            // Create a handler for processing our GWT app
            WebAppContext handler = new WebAppContext();
            handler.setContextPath("/");
            handler.setWar("D:\\dev\\ModbusControl\\modctrl-webapp\\target\\modctrl-webapp-0.0.1-SNAPSHOT.war");
            
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

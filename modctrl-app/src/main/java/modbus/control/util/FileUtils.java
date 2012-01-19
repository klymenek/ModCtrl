/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modbus.control.util;

import java.io.File;

/**
 *
 * @author ares
 */
public class FileUtils {
    public static void main(String[] args) {
        listFiles();
    }
    
    public static void listFiles() {
        File f = new File("D:\\dev\\ModCtrl\\modctrl-app\\target\\staging\\ModbusControl\\lib");
        
        int n = 1;
        for (File fi : f.listFiles()) {
            System.out.println("wrapper.java.classpath." + n + "=lib\\\\" + fi.getName());
            n++;
        }
        
        
        for (File fi : f.listFiles()) {
            System.out.println("<jar src=\"" + fi.getAbsolutePath() + "\"/>");
        }
    }
}

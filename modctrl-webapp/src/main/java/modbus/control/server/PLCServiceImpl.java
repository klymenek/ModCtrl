package modbus.control.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import modbus.control.client.rpc.PLCService;
import modbus.control.server.modbus.ModbusQuery;
import modbus.control.shared.ProcessVar;

/**
 * 
 * @author ares
 */
@SuppressWarnings("serial")
public class PLCServiceImpl extends RemoteServiceServlet implements
        PLCService {

    /**
     * @param args
     *            - the command line arguments
     */
    public Integer writeCoil(Boolean state, ProcessVar var) {

        return ModbusQuery.writeCoil(state, var);
    }// main

    @Override
    public Boolean readCoil(ProcessVar var) {

        return ModbusQuery.readCoil(var);
    }
}

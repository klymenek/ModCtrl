package modbus.control.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import modbus.control.api.model.ProcessVar;
import modbus.control.api.modbus.ModbusQuery;
import modbus.control.client.rpc.PLCService;

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
    @Override
    public Integer writeCoil(Boolean state, ProcessVar var) {

        return ModbusQuery.writeCoil(state, var);
    }// main

    @Override
    public Boolean readCoil(ProcessVar var) {

        return ModbusQuery.readCoil(var);
    }
}

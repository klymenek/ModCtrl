package modbus.control.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import modbus.control.api.modbus.ModbusQuery;
import modbus.control.client.rpc.PLCService;
import modbus.control.shared.ProcessVarJso;

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
    public Integer writeCoil(Boolean state, ProcessVarJso var) {

        return ModbusQuery.writeCoil(state, var);
    }// main

    @Override
    public Boolean readCoil(ProcessVarJso var) {

        return ModbusQuery.readCoil(var);
    }
}

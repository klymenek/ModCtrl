package modbus.control.server.modbus;

import java.net.InetAddress;

import modbus.control.shared.ProcessVar;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.io.ModbusUDPTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesRequest;
import net.wimpi.modbus.msg.ReadInputDiscretesResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.net.UDPMasterConnection;
import org.apache.log4j.Logger;

public class ModbusQuery {

    private static final Logger logger = Logger.getLogger(ModbusQuery.class);

    enum PROTOCOL {

        UDP, TCP
    };
    static PROTOCOL prot = PROTOCOL.TCP; //TODO UDP moeglich???

    public synchronized static Integer writeCoil(Boolean state, ProcessVar var) {
        switch (prot) {
            case UDP:
                return writeCoilUDP(state, var);
            case TCP:
                return writeCoilTCP(state, var);
            default:
                return writeCoilUDP(state, var);
        }
    }

    public synchronized static Boolean readCoil(ProcessVar var) {
        switch (prot) {
            case UDP:
                return readCoilUDP(var);
            case TCP:
                return readCoilTCP(var);
            default:
                return readCoilUDP(var);
        }
    }

    public static Integer writeCoilTCP(Boolean state, ProcessVar var) {
        InetAddress addr = null;
        TCPMasterConnection con = null;
        WriteCoilRequest req = null;
        ModbusTCPTransaction trans = null;

        try {
            // 1. Set Parameters
            addr = InetAddress.getByName(var.getPlc().getIp());

            // 2. Open the connection
            con = new TCPMasterConnection(addr);
            con.setPort(var.getPlc().getPort());
            con.connect();

            logger.debug("Connected to " + addr.toString() + ":"
                    + con.getPort());

            // 3. Prepare the requests
            req = new WriteCoilRequest();
            req.setReference(var.getModbusaddr());
            req.setUnitID(0);

            // 4. Prepare the transactions
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            trans.setReconnecting(false);

            // 5. Execute the transaction
            req.setCoil(state);
            trans.execute();

            logger.debug("Updated coil with state: " + state);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 6. Close the connection
            con.close();
        }

        return 1;
    }

    public static Boolean readCoilTCP(ProcessVar var) {
        InetAddress addr = null;
        TCPMasterConnection con = null;
        ModbusRequest req = null;

        ModbusTCPTransaction trans = null;

        boolean in = false;

        try {

            // 1. Setup the parameters
            addr = InetAddress.getByName(var.getPlc().getIp());

            // 2. Open the connection
            con = new TCPMasterConnection(addr);
            con.setPort(var.getPlc().getPort());
            con.connect();
            logger.debug("Connected to " + addr.toString() + ":"
                    + con.getPort());


            // 3. Prepare the requests
            req = new ReadInputDiscretesRequest(var.getModbusaddr(), 1);

            req.setUnitID(0);

            // 4. Prepare the transactions
            trans = new ModbusTCPTransaction(con);
            trans.setRequest(req);
            trans.setReconnecting(false);

            // 6. Execute the transaction
            trans.execute();
            in = ((ReadInputDiscretesResponse) trans.getResponse()).getDiscreteStatus(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 7. Close the connection
            con.close();
        }

        return in;
    }

    public static Integer writeCoilUDP(Boolean state, ProcessVar var) {
        // Debugging Ausgaben aktivieren
        System.setProperty("net.wimpi.modbus.debug", "true");

        InetAddress addr = null;
        UDPMasterConnection con = null;
        WriteCoilRequest req = null;
        ModbusUDPTransaction trans = null;

        try {
            // 1. Set Parameters
            addr = InetAddress.getByName(var.getPlc().getIp());

            // 2. Open the connection
            con = new UDPMasterConnection(addr);
            con.setPort(var.getPlc().getPort());
            con.connect();

            if (Modbus.debug) {
                System.out.println("Connected to " + addr.toString() + ":"
                        + con.getPort());
            }

            // 3. Prepare the requests
            req = new WriteCoilRequest();
            req.setReference(var.getModbusaddr());
            req.setUnitID(0);

            // 4. Prepare the transactions
            trans = new ModbusUDPTransaction(con);
            trans.setRequest(req);

            // 5. Execute the transaction
            req.setCoil(state);
            trans.execute();

            if (Modbus.debug) {
                System.out.println("Updated coil with state: " + state);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 6. Close the connection
            con.close();
        }

        return 1;
    }

    public static Boolean readCoilUDP(ProcessVar var) {
        InetAddress addr = null;
        UDPMasterConnection con = null;
        ModbusRequest req = null;

        ModbusUDPTransaction trans = null;

        boolean in = false;

        try {

            // 1. Setup the parameters
            addr = InetAddress.getByName(var.getPlc().getIp());

            // 2. Open the connection
            con = new UDPMasterConnection(addr);
            con.setPort(var.getPlc().getPort());
            con.connect();
            if (Modbus.debug) {
                System.out.println("Connected to " + addr.toString() + ":"
                        + con.getPort());
            }

            // 3. Prepare the requests
            req = new ReadInputDiscretesRequest(var.getModbusaddr(), 1);

            req.setUnitID(0);

            // 4. Prepare the transactions
            trans = new ModbusUDPTransaction(con);
            trans.setRequest(req);

            // 6. Execute the transaction
            trans.execute();
            in = ((ReadInputDiscretesResponse) trans.getResponse()).getDiscreteStatus(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 7. Close the connection
            con.close();
        }

        return in;
    }
}

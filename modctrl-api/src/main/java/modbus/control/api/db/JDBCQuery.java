package modbus.control.api.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modbus.control.api.model.CategoryImpl;
import modbus.control.api.model.PLCImpl;
import modbus.control.api.model.ProcessVarImpl;

@Deprecated
public class JDBCQuery {

    public static List<CategoryImpl> getCategory(Connection con)
            throws SQLException {
        ArrayList<CategoryImpl> category = new ArrayList<CategoryImpl>();

        Statement stmt = con.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM CATEGORYS");

        while (results.next()) {
            CategoryImpl r = new CategoryImpl();

            r.setId(results.getInt("ID"));
            r.setName(results.getString("NAME"));

            category.add(r);
        }

        return category;
    }

    public static PLCImpl getPLC(int id, Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM MODBUS_PLCS WHERE ID = ?");
        pstmt.setInt(1, id);

        ResultSet results = pstmt.executeQuery();

        PLCImpl plc = new PLCImpl();

        if (results.next()) {
            plc.setId(results.getInt("ID"));
            plc.setName(results.getString("NAME"));
            plc.setIp(results.getString("IP"));
            plc.setPort(results.getInt("PORT"));
        }

        return plc;
    }

    /**
     * TODO Tabellenname nicht auf PROTOKOLL bezogen, variabel?
     * 
     * 
     * @param category
     * @param con
     * @return
     * @throws SQLException
     */
    public static List<ProcessVarImpl> getVars(CategoryImpl category, Connection con)
            throws SQLException {
        ArrayList<ProcessVarImpl> vars = new ArrayList<ProcessVarImpl>();

        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM MODBUS_DIGITAL WHERE CATEGORY = ?");
        pstmt.setInt(1, category.getId());

        ResultSet results = pstmt.executeQuery();
        while (results.next()) {
            ProcessVarImpl pv = new ProcessVarImpl();

            pv.setId(results.getInt("ID"));
            pv.setName(results.getString("NAME"));
            pv.setDescription(results.getString("DESCRIPTION"));
            pv.setModbusaddr(results.getInt("MODBUSADDR"));

            pv.setCategory(category);

            pv.setPlc(getPLC(results.getInt("PLC"), con));

            vars.add(pv);
        }

        return vars;
    }
}

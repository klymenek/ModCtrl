package modbus.control.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modbus.control.shared.PLC;
import modbus.control.shared.ProcessVar;
import modbus.control.shared.Category;

public class JDBCQuery {

    public static List<Category> getCategory(Connection con)
            throws SQLException {
        ArrayList<Category> category = new ArrayList<Category>();

        Statement stmt = con.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM CATEGORYS");

        while (results.next()) {
            Category r = new Category();

            r.setId(results.getInt("ID"));
            r.setName(results.getString("NAME"));

            category.add(r);
        }

        return category;
    }

    public static PLC getPLC(int id, Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM MODBUS_PLCS WHERE ID = ?");
        pstmt.setInt(1, id);

        ResultSet results = pstmt.executeQuery();

        PLC plc = new PLC();

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
    public static List<ProcessVar> getVars(Category category, Connection con)
            throws SQLException {
        ArrayList<ProcessVar> vars = new ArrayList<ProcessVar>();

        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM MODBUS_DIGITAL WHERE CATEGORY = ?");
        pstmt.setInt(1, category.getId());

        ResultSet results = pstmt.executeQuery();
        while (results.next()) {
            ProcessVar pv = new ProcessVar();

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

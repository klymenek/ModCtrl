package modbus.control.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import modbus.control.client.rpc.DatabaseService;
import modbus.control.server.db.JDBCQuery;
import modbus.control.shared.ProcessVar;
import modbus.control.shared.Category;

/**
 * 
 * @author ares
 */
@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements
        DatabaseService {

    @Override
    public List<Category> getCategorys() {
        try {
            return JDBCQuery.getCategory((Connection) getServletContext().getAttribute("connection"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProcessVar> getVarsByCategory(Category category) {
        try {
            return JDBCQuery.getVars(category, (Connection) getServletContext().getAttribute("connection"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

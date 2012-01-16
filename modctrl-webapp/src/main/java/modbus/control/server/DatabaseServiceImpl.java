package modbus.control.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.Connection;
import java.util.List;
import modbus.control.api.db.GroovySqlQuery;
import modbus.control.client.rpc.DatabaseService;
import modbus.control.shared.CategoryJso;
import modbus.control.shared.ProcessVarJso;

/**
 * 
 * @author ares
 */
@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements
        DatabaseService {

    @Override
    public List<CategoryJso> getCategory() {        
        return GroovySqlQuery.getCategory((Connection) getServletContext().getAttribute("connection"));
        
//        try {
//            return JDBCQuery.getCategoryJso((Connection) getServletContext().getAttribute("connection"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    @Override
    public List<ProcessVarJso> getVarsByCategory(CategoryJso category) {
        return GroovySqlQuery.getVars((Connection) getServletContext().getAttribute("connection"), category);
        
//        try {
//            return JDBCQuery.getVars(CategoryJso, (Connection) getServletContext().getAttribute("connection"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}

package modbus.control.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.Connection;
import java.util.List;
import modbus.control.GroovySqlQuery;
import modbus.control.client.rpc.DatabaseService;
import modbus.control.shared.Category;
import modbus.control.shared.ProcessVar;

/**
 * 
 * @author ares
 */
@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements
        DatabaseService {

    @Override
    public List<Category> getCategorys() {        
        return GroovySqlQuery.getCategory((Connection) getServletContext().getAttribute("connection"));
        
//        try {
//            return JDBCQuery.getCategory((Connection) getServletContext().getAttribute("connection"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    @Override
    public List<ProcessVar> getVarsByCategory(Category category) {
        return GroovySqlQuery.getVars((Connection) getServletContext().getAttribute("connection"), category);
        
//        try {
//            return JDBCQuery.getVars(category, (Connection) getServletContext().getAttribute("connection"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}

package modbus.control.api.db

import java.sql.Connection
import java.util.List
import java.util.ArrayList
import modbus.control.api.model.Category
import modbus.control.api.model.PLC
import modbus.control.api.model.ProcessVar
import groovy.sql.Sql

/**
 *
 * @author ares
 */
class GroovySqlQuery { 
    private static def db = [url:'jdbc:h2:~/wagocontrol', user:'sa', password:'12qwertz.-', driver:'org.h2.Driver']
    private static def categoryQueryAll = "SELECT * FROM CATEGORYS"
    private static def varQueryByCategory = "SELECT * FROM MODBUS_DIGITAL WHERE CATEGORY = ?"
    private static def plcQueryById = "SELECT * FROM MODBUS_PLCS WHERE ID = ?"
    
    static List<Category> getCategory(Connection db) {
        def sql = new Sql(db)
        def categorys = new ArrayList<Category>()
        
        sql.eachRow(categoryQueryAll) { row -> 
            def c = new Category()
            c.setId(row.id)
            c.setName(row.name)
            
            categorys.add(c)
        }
        categorys
    }
    
    static List<ProcessVar> getVars(Connection db, Category category) {
        def sql = new Sql(db)
        def vars = new ArrayList<ProcessVar>()
        
        sql.eachRow(varQueryByCategory, [category.getId()]) { row -> 
            def p = new ProcessVar()
            p.setId(row.id)
            p.setName(row.name)
            p.setDescription(row.description)
            p.setModbusaddr(row.modbusaddr)
            
            p.setCategory(category)
            
            def prow = sql.firstRow(plcQueryById, [row.plc])
            def plc = new PLC()
            plc.setId(prow.id)
            plc.setIp(prow.ip)
            plc.setName(prow.name)
            plc.setPort(prow.port)
            
            p.setPlc(plc)
            
            vars.add(p)
        }
        vars
    }    
    
    static void main(String[] args) {
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
                     
        sql.eachRow(categoryQueryAll) { row -> println row.name }
        
        def c = new Category()
        c.setId(1)
        
        println getVars(sql, c)
    }
}
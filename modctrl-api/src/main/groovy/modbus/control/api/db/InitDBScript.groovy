/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modbus.control.api.db

import groovy.sql.Sql

def database = System.getProperty("user.dir") + "\\src\\main\\resources\\db"
if(!new File(database).exists()) {
    database = System.getProperty("user.dir") + "\\modctrl-api\\src\\main\\resources\\db\\wagocontrol"
} else {
    database += "\\wagocontrol"
}

if(!(new File(database + ".h2.db")).exists()) {             
    def db = [url:"jdbc:h2:$database", user:'sa', password:'12qwertz.-', driver:'org.h2.Driver']
    def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)        
        
    def scriptfile = System.getProperty("user.dir") + "\\src\\main\\resources\\db_init.sql"
                     
    sql.execute("RUNSCRIPT FROM '$scriptfile'")
}

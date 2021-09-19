/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import static java.rmi.server.LogStream.log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author WilliamTrung
 */
public class DBConnection {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=FPTEventManagement";
        Connection conn = DriverManager.getConnection(url, "sa", "123");
        return conn;
    }
    public static Connection getConnection1() throws NamingException,SQLException{
        Context context = new InitialContext();
        Context end = (Context)context.lookup("java:comp/env");
        DataSource ds = (DataSource)end.lookup("DBConnection");
        Connection conn=ds.getConnection();
        return conn;
    }
    public static void closeQueryConnection(Connection conn, PreparedStatement stm, ResultSet rs){
        try {
            if (conn != null) {
                conn.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            log("Error at closing connection" + e.toString());
        }
    }
}

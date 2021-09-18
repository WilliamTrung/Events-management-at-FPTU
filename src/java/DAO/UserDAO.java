/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.UserDTO;
import Utils.DBConnection;
import static java.rmi.server.LogStream.log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author WilliamTrung
 */
public class UserDAO {
    public UserDTO checkUser(UserDTO user){
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection1();
            if (conn!=null) {
                String sql = "SELECT username "
                        + "FROM tblUsers "
                        + "WHERE id = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(2, user.getId());
                
                rs = stm.executeQuery();
                if(rs.next()){
                    user.setUsername(rs.getString("username"));
                } else {
                    
                }
            }
        } catch (Exception e) {
            log("Error at UserDAO - checkUser" + e.toString());
        }
        return user;
    }
    private UserDTO createUser(UserDTO user){
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection1();
            if (conn!=null) {
                String sql = "INSERT INTO tblUsers(id, email, username, statusId, roleId, hd, picture) "
                        + "VALUES(?,?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(2, user.getId());
                
                rs = stm.executeQuery();
                if(rs.next()){
                    
                } else {
                    
                }
            }
        } catch (Exception e) {
            log("Error at UserDAO - createUser" + e.toString());
        }
        return user;
    }
    private UserDTO loginUser(UserDTO user){
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection1();
            if (conn!=null) {
                String sql = "SELECT id, statusId, roleId FROM tblUsers WHERE id = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(2, user.getId());
                
                rs = stm.executeQuery();
                if(rs.next()){
                    
                } else {
                    
                }
            }
        } catch (Exception e) {
            log("Error at UserDAO - loginUser" + e.toString());
        }
        return user;
    }
}

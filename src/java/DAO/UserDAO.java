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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WilliamTrung
 */
public class UserDAO {
    public UserDTO loginUser(UserDTO user){
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if (conn!=null) {
                String sql = "SELECT username, roleId, statusId "
                        + "FROM tblUsers "
                        + "WHERE userId = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, user.getUserId());
                
                rs = stm.executeQuery();
                if(rs.next()){
                    user.setUsername(rs.getString("username"));
                    user.setRoleId(rs.getString("roleId"));
                    user.setStatusId(rs.getString("statusId"));
                } else {
                    if(createUser(user, conn)==false){
                        user=null;
                    }
                }
            }
        } catch (Exception e) {
            log("Error at UserDAO - checkUser: " + e.toString());
            user = null;
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return user;
    }
    private boolean createUser(UserDTO user, Connection conn){
        PreparedStatement stm = null;
        boolean check = false;
        try {
            if (conn!=null) {
                String sql = "INSERT INTO tblUsers(userId, email, username, statusId, roleId) "
                        + "VALUES(?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, user.getUserId());
                stm.setString(2, user.getEmail());
                stm.setString(3, (user.getEmail().split("@"))[0]);//get the email without @fpt.edu.vn as the username
                stm.setString(4, "A");// status == ACTIVE
                stm.setString(5, "US"); // USER role
                check = stm.executeUpdate() > 0;             
            }
        } catch (Exception e) {
            log("Error at UserDAO - createUser: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }
    public List<UserDTO> getListUsers(String search){
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT userId, email, username, statusId, roleId "
                    + "FROM tblUsers WHERE username like ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("userId");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String statusId = rs.getString("statusId");
                String roleId = rs.getString("roleId");
                
                list.add(new UserDTO(userId, email, username, statusId, roleId, true, null, null));
            }
        } catch (Exception e) {
            log("Error at UserDAO - getListUsers: " + e.toString());
        }
        return list;
    }
}

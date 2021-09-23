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
    private String getStatusName(String statusId, Connection conn){
        PreparedStatement stm = null;
        ResultSet rs = null;
        String status = null;
        try {
            if (conn!=null) {
                String sql = "SELECT statusName "
                        + "FROM tblStatusUser "
                        + "WHERE statusId = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, statusId);

                rs = stm.executeQuery();
                status = rs.getString("statusName");
            }
        } catch (Exception e) {
            log("Error at UserDAO - getStatusName: " + e.toString());
        } 
        return status;
    }
    private String getRoleName(String roleId, Connection conn){
        PreparedStatement stm = null;
        ResultSet rs = null;
        String role = null;
        try {
            if (conn!=null) {
                String sql = "SELECT roleName "
                        + "FROM tblRoles "
                        + "WHERE statusId = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, roleId);

                rs = stm.executeQuery();
                role = rs.getString("roleName");
            }
        } catch (Exception e) {
            log("Error at UserDAO - getRoleName: " + e.toString());
        } 
        return role;
    }
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
                    user.setRole(getRoleName(rs.getString("roleId"), conn));
                    user.setStatus(getStatusName(rs.getString("statusId"), conn));
                } else {
                    if(createUser(user, conn)==false){
                        user=null;
                    } else {
                        UserDTO loginUser = new UserDTO(user.getUserId(), user.getEmail(), user.getUsername(), getStatusName("A", conn), getRoleName("US", conn), true, user.getHd(), user.getPicture());
                        user = loginUser;
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
                String status = getStatusName(rs.getString("statusId"), conn);
                String role = getRoleName(rs.getString("roleId"), conn);
                
                list.add(new UserDTO(userId, email, username, status, role, true, null, null));
            }
        } catch (Exception e) {
            log("Error at UserDAO - getListUsers: " + e.toString());
        }
        return list;
    }
    
    public UserDTO getUserById(String userId){
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT email, username, statusId, roleId "
                    + "FROM tblUsers WHERE userId like ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + userId + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String username = rs.getString("username");
                String status = getStatusName(rs.getString("statusId"), conn);
                String role = getRoleName(rs.getString("roleId"), conn);
                
                user = new UserDTO(userId, email, username, status, role, true, null, null);
            }
        } catch (Exception e) {
            log("Error at UserDAO - getListUsers: " + e.toString());
        }
        return user;
    }
    public boolean updateUser(UserDTO user){
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            
        } catch (Exception e) {
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }
}

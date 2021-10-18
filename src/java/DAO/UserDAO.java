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
    public List<String> getRoleList(){
        List<String> list = new ArrayList<>();
        PreparedStatement stm = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if (conn!=null) {
                String sql = "SELECT roleName "
                        + "FROM tblRoles ";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    String roleName = rs.getString("roleName");
                    list.add(roleName);
                }
            }
        } 
        catch (Exception e) 
        {
            log("Error at UserDAO - getRoleList: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
    public UserDTO loginUser(UserDTO user) {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                String sql = "SELECT username, r.roleName, s.statusName "
                        + "FROM tblUsers u, tblStatusUser s, tblRoles r "
                        + "WHERE u.userId = ? AND u.statusId = s.statusId AND u.roleId = r.roleId";
                stm = conn.prepareStatement(sql);
                stm.setString(1, user.getUserId());

                rs = stm.executeQuery();
                if (rs.next()) {
                    user.setUsername(rs.getString("username"));
                    user.setRole(rs.getString("roleName"));
                    user.setStatus(rs.getString("statusName"));
                } else {
                    if (createUser(user, conn) == false) {
                        user = null;
                    } else {
                        user = loginUser(user);
                    }
                }
            }
        } catch (Exception e) {
            log("Error at UserDAO - loginUser: " + e.toString());
            user = null;
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return user;
    }

    private boolean createUser(UserDTO user, Connection conn) {
        PreparedStatement stm = null;
        boolean check = false;
        try {
            if (conn != null) {
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
        } 
        return check;
    }

    public List<UserDTO> getListUsers(String search) {
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT userId, email, username, s.statusName, r.roleName "
                    + "FROM tblUsers u, tblStatusUser s, tblRoles r WHERE username like ? AND u.statusId = s.statusId AND u.roleId = r.roleId";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("userId");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String status = rs.getString("statusName");
                String role = rs.getString("roleName");

                list.add(new UserDTO(userId, email, username, status, role, true, null, null));
            }
        } catch (Exception e) {
            log("Error at UserDAO - getListUsers: " + e.toString());
        }
        return list;
    }

    public UserDTO getUserById(String userId) {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT email, username, s.statusName, r.roleName "
                    + "FROM tblUsers u, tblStatusUser s, tblRoles r  "
                    + "WHERE userId like ? AND u.statusId = s.statusId AND u.roleId = r.roleId";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + userId + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String username = rs.getString("username");
                String status = rs.getString("statusName");
                String role = rs.getString("roleName");

                user = new UserDTO(userId, email, username, status, role, true, null, null);
            }
        } catch (Exception e) {
            log("Error at UserDAO - getUsersById " + e.toString());
        }
        return user;
    }

    public boolean updateUser(UserDTO user) {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblUsers "
                        + "SET roleId = (SELECT roleId FROM tblRoles WHERE roleName = ? ) "
                        + "WHERE userId = ? ";
                stm = conn.prepareStatement(sql);
                stm.setString( 1, user.getRole());
                stm.setString(2, user.getUserId());
                
                check = stm.executeUpdate() >0;
            }

        } catch (Exception e) {
            log("Error at UserDAO - updateUser: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }
    public boolean updateUsername(UserDTO user) {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblUsers "
                        + "SET username = ? "
                        + "WHERE userId = ? ";
                stm = conn.prepareStatement(sql);
                stm.setString( 1, user.getUsername());
                stm.setString(2, user.getUserId());
                
                check = stm.executeUpdate() >0;
            }

        } catch (Exception e) {
            log("Error at UserDAO - updateUsername: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }
    public boolean updateStatus(UserDTO user) {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblUsers "
                        + "SET statusId = (SELECT statusId FROM tblStatusUser WHERE statusName = ? ) "
                        + "WHERE userId = ? ";
                stm = conn.prepareStatement(sql);
                
                if(user.getStatus().equals("Active")){
                    user.setStatus("Deactive");
                } else {
                    user.setStatus("Active");
                }
                stm.setString( 1, user.getStatus());
                stm.setString(2, user.getUserId());
                
                check = stm.executeUpdate() >0;
            }

        } catch (Exception e) {
            log("Error at UserDAO - updateStatus: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }
}

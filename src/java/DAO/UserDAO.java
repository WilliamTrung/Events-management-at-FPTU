/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.UserDTO;
import Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author WilliamTrung
 */
public class UserDAO {
    public UserDTO checkUser(UserDTO user) throws Exception{
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection1();
            if (conn!=null) {
                String sql = "SELECT username, statusId, roleId FROM tblUsers";
                stm = conn.prepareStatement(sql);
                stm.setString(2, user.getEmail());
            }
        } catch (Exception e) {
        }
        return user;
    }
}

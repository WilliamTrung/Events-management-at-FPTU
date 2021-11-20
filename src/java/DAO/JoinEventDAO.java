/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.EventDTO;
import DTO.LocationDTO;
import DTO.SlotDTO;
import DTO.UserDTO;
import Utils.DBConnection;
import static java.rmi.server.LogStream.log;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WilliamTrung
 */
public class JoinEventDAO {
    public int checkJoinEvent(EventDTO event, UserDTO user)throws SQLException{
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = -1;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT joinStatus "
                    + "FROM tblParticipants "
                    + "WHERE userId = ? AND eventId = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(2, event.getEventId());
            stm.setString(1, user.getUserId());
            rs = stm.executeQuery();
            if(rs.next()){
                result = rs.getInt("joinStatus");
            }
        } catch (Exception e) {
            log("Error at JoinEventDAO - checkJoinEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return result;
    }
    public boolean joinEvent(EventDTO event, UserDTO user) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO tblParticipants(eventId, userId, joinStatus) "
                    + "VALUES (?,?,?) ";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, event.getEventId());
            stm.setString(2, user.getUserId());
            stm.setInt(3, 1);
            result = stm.executeUpdate() > 0;
            
        } catch (Exception e) {
            log("Error at JoinEventDAO - joinEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return result;
    }
    public boolean checkSeat(EventDTO event, UserDTO user){
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = true;
        try {
            int seat = 0;
            conn = DBConnection.getConnection();
            String sql = "SELECT COUNT(userId) "
                    + "FROM tblParticipants "
                    + "WHERE  eventId = ? AND userId != ? AND joinStatus = 1";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, event.getEventId());
            stm.setString(2, user.getUserId());
            rs = stm.executeQuery();
            if(rs.next()){
                seat = rs.getInt(1);
            }
            if(seat == event.getLocation().getSeat()){
                result = false;
            }
                    
        } catch (Exception e) {
            log("Error at JoinEventDAO - checkSeat: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return result;
    }
    public boolean updateJoinEvent(EventDTO event, UserDTO user, int currentStatus) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE tblParticipants "
                    + "SET joinStatus = ? "
                    + "WHERE userId = ? AND eventId = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, currentStatus == 0 ? 1 : 0); //
            stm.setInt(3, event.getEventId());
            stm.setString(2, user.getUserId());
            result = stm.executeUpdate() > 0;
            
        } catch (Exception e) {
            log("Error at JoinEventDAO - updateJoinEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return result;
    }
}

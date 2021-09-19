/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.EventDTO;
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
 * @author Admin
 */
public class EventDAO {

    public List<EventDTO> getListEvent(String search) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT eventId, userId, title, description, location, createDatetime, startDatetime, endDatetime, statusId"
                    + "FROM tblEvents WHERE title like ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                int eventId = rs.getInt("eventId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                Date createDatetime = rs.getDate("createDatetime");
                Date startDatetime = rs.getDate("startDatetime");
                Date endDatetime = rs.getDate("endDatetime");
                String statusId = rs.getString("statusId");
                list.add(new EventDTO(eventId, userId, title, description, location, createDatetime, startDatetime, endDatetime, statusId));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }

    public boolean createEvent(EventDTO newEvent) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean flag = false;
        try {
            conn = Utils.DBConnection.getConnection1();
            String sql = "INSERT INTO tblEvents (eventId, userId, title, description, location, createDatetime, startDatetime, endDatetime, statusId)"
                    + "VALUES (?,?,?,?,?,?,?,?)";
            stm = conn.prepareStatement(sql);
            int eventId = newEvent.getEventId();
            String userId = newEvent.getUserId();
            String title = newEvent.getTitle();
            String description = newEvent.getDescription();
            String location = newEvent.getLocation();
            Date createDatetime = newEvent.getCreateDatetime();
            Date startDatetime = newEvent.getStartDatetime();
            Date endDatetime = newEvent.getEndDatetime();
            stm.setInt(1, eventId);
            stm.setString(2, userId);
            stm.setString(3, title);
            stm.setString(4, description);
            stm.setString(5, location);
            stm.setDate(6, createDatetime);
            stm.setDate(7, startDatetime);
            stm.setDate(8, endDatetime);
            stm.setString(9, "Pending");
            flag = stm.executeUpdate(sql) > 0;
        } catch (Exception e) {
            log("Error at EventDAO - createEvent: " + e.toString());
        } finally {
            Utils.DBConnection.closeQueryConnection(conn, stm, null);
        }
        return flag;
    }

    public boolean updateEvent(EventDTO newEvent) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = Utils.DBConnection.getConnection1();
            String sql = "UPDATE tblEvents SET title=?, description=?, location=?, createDatetime=?, startDatetime=?, endDatetime=?,statusId=?"
                    + "WHERE eventId=? AND userId=?";
            stm = conn.prepareStatement(sql);
            int eventId = newEvent.getEventId();
            String userId = newEvent.getUserId();
            String title = newEvent.getTitle();
            String description = newEvent.getDescription();
            String location = newEvent.getLocation();
            Date createDatetime = newEvent.getCreateDatetime();
            Date startDatetime = newEvent.getStartDatetime();
            Date endDatetime = newEvent.getEndDatetime();
            String statusId = newEvent.getStatusId();
            stm.setString(1, title);
            stm.setString(2, description);
            stm.setString(3, location);
            stm.setDate(4, createDatetime);
            stm.setDate(5, startDatetime);
            stm.setDate(6, endDatetime);
            stm.setString(7, statusId);
            stm.setInt(8, eventId);
            stm.setString(9, userId);
            check = stm.executeUpdate(sql) > 0;
        } catch (Exception e) {
            log("Error at EventDAO - updateEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }
    
    public boolean deleteEvent(int eventID) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = Utils.DBConnection.getConnection1();
            if (conn != null) {
                String sql = " DELETE tblEvents "
                        + " WHERE eventID=? ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, eventID);
                result = stm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
}

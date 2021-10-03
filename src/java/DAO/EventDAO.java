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
 * @author Admin
 */

public class EventDAO {
    public int getLastId(){
        //get the newest created id from tblEvents
        //SELECT MAX(eventId) FROM tblEvents
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int eventId = 0;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT MAX(eventId) FROM tblEvents";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                eventId = rs.getInt("eventId");               
            }
        } catch (Exception e) {
            log("Error at EventDAO - getLastId: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return eventId;
    }
    public List<EventDTO> getListEvent(String search) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT eventId, userId, title, description, locationId, createDatetime, slotId, s.statusName "
                    + "FROM tblEvents e, tblStatusEvent s "
                    + "WHERE title like ? AND s.statusId = e.statusId";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                SlotDAO sDao = new SlotDAO();
                int eventId = rs.getInt("eventId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String description = rs.getString("description"); 
                String locationId = rs.getString("locationId");
                Date createDatetime = rs.getDate("createDatetime");
                String slotId = rs.getString("slotId");
                String status = rs.getString("statusName");
                
                UserDTO user = new UserDAO().getUserById(userId);
                SlotDTO slot = sDao.getSlotById(slotId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                list.add(new EventDTO(eventId, user, title, description, location, createDatetime, slot, status));
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
            String sql = "INSERT INTO tblEvents (userId, title, description, locationId, createDatetime, slotId, statusId) "
                    + "VALUES (?,?,?,?,?,?, SELECT statusId FROM tblStatusEvent WHERE statusName = ?)";
            stm = conn.prepareStatement(sql);
            String userId = newEvent.getUser().getUserId();
            String title = newEvent.getTitle();
            String description = newEvent.getDescription();
            String locationId = newEvent.getLocation().getLocationId();
            Date createDatetime = newEvent.getCreateDatetime();
            String slotId = newEvent.getSlot().getSlotId();
            stm.setString(2, userId);
            stm.setString(3, title);
            stm.setString(4, description);
            stm.setString(5, locationId);
            stm.setDate(6, createDatetime);
            stm.setString(7, slotId);
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
            String sql = "UPDATE tblEvents SET title=?, description=?, locationId=?, slotId=?, statusId = (SELECT statusId FROM tblStatusEvent WHERE statusName = ?) "
                    + "WHERE eventId=? AND userId=?";
            stm = conn.prepareStatement(sql);
            int eventId = newEvent.getEventId();
            String userId = newEvent.getUser().getUserId();
            String title = newEvent.getTitle();
            String description = newEvent.getDescription();
            String locationId = newEvent.getLocation().getLocationId();
            String slotId= newEvent.getSlot().getSlotId();
            String status = newEvent.getStatus();
            
            stm.setString(1, title);
            stm.setString(2, description);
            stm.setString(3, locationId);
            stm.setString(5, slotId);
            stm.setString(6, status);
            stm.setInt(7, eventId);
            stm.setString(8, userId);
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
                result = stm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            log("Error at EventDAO - deleteEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return result;
    }
}

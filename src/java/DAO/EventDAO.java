/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.EventDTO;
import DTO.LocationDTO;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
            String sql = "SELECT eventId, userId, title, description, locationId, createDatetime, startDatetime, endDatetime, statusId, picture "
                    + "FROM tblEvents WHERE title like ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                int eventId = rs.getInt("eventId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String locationId = rs.getString("locationId");
                Date createDatetime = rs.getDate("createDatetime");
                Date startDatetime = rs.getDate("startDatetime");
                Date endDatetime = rs.getDate("endDatetime");
                String picture = rs.getString("picture");
                String statusId = rs.getString("statusId");

                UserDTO user = new UserDAO().getUserById(userId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                list.add(new EventDTO(eventId, user, title, description, location, createDatetime, startDatetime, endDatetime, statusId, picture));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }

    public List<EventDTO> getListEventByPage(String search, int index, int pageSize) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "WITH tblEventPage AS (SELECT (ROW_NUMBER() over (order by startDatetime) ) AS RowNum,\n"
                    + "					eventId, userId, title, description, locationId, createDatetime, startDatetime, endDatetime, statusId, picture \n"
                    + "				FROM tblEvents \n"
                    + "				WHERE title like ?)\n"
                    + "SELECT eventId, userId, title, description, locationId, createDatetime, startDatetime, endDatetime, statusId, picture\n"
                    + "FROM tblEventPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            //index*pageSize - (pageSize-1) AND index*pageSize
            stm.setInt(2, index);
            stm.setInt(3, pageSize);
            stm.setInt(4, pageSize);
            stm.setInt(5, index);
            stm.setInt(6, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                int eventId = rs.getInt("eventId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String locationId = rs.getString("locationId");
                Date createDatetime = rs.getDate("createDatetime");
                Date startDatetime = rs.getDate("startDatetime");
                Date endDatetime = rs.getDate("endDatetime");
                String picture = rs.getString("picture");
                String statusId = rs.getString("statusId");

                UserDTO user = new UserDAO().getUserById(userId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                list.add(new EventDTO(eventId, user, title, description, location, createDatetime, startDatetime, endDatetime, statusId, picture));
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
            String sql = "INSERT INTO tblEvents (userId, title, description, locationId, createDatetime, startDatetime, endDatetime, statusId, picture) "
                    + "VALUES (?,?,?,?,?,?,?)";
            stm = conn.prepareStatement(sql);
            String userId = newEvent.getUser().getUserId();
            String title = newEvent.getTitle();
            String description = newEvent.getDescription();
            String locationId = newEvent.getLocation().getLocationId();
            Date createDatetime = newEvent.getCreateDatetime();
            Date startDatetime = newEvent.getStartDatetime();
            Date endDatetime = newEvent.getEndDatetime();
            String picture = newEvent.getPicture();
            stm.setString(2, userId);
            stm.setString(3, title);
            stm.setString(4, description);
            stm.setString(5, locationId);
            stm.setDate(6, createDatetime);
            stm.setDate(7, startDatetime);
            stm.setDate(8, endDatetime);
            stm.setString(9, "Pending");
            stm.setString(10, picture);
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
            String sql = "UPDATE tblEvents SET title=?, description=?, locationId=?, createDatetime=?, startDatetime=?, endDatetime=?, statusId = (SELECT statusId FROM tblStatusEvent WHERE statusName = ?), picture=?"
                    + "WHERE eventId=? AND userId=?";
            stm = conn.prepareStatement(sql);
            int eventId = newEvent.getEventId();
            String userId = newEvent.getUser().getUserId();
            String title = newEvent.getTitle();
            String description = newEvent.getDescription();
            String locationId = newEvent.getLocation().getLocationId();
            Date createDatetime = newEvent.getCreateDatetime();
            Date startDatetime = newEvent.getStartDatetime();
            Date endDatetime = newEvent.getEndDatetime();
            String statusId = newEvent.getStatus();
            String picture = newEvent.getPicture();
            stm.setString(1, title);
            stm.setString(2, description);
            stm.setString(3, locationId);
            stm.setDate(4, createDatetime);
            stm.setDate(5, startDatetime);
            stm.setDate(6, endDatetime);
            stm.setString(7, statusId);
            stm.setString(8, picture);
            stm.setInt(9, eventId);
            stm.setString(10, userId);
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
    public int countListEvent(){
        int rs=0;
        EventDAO dao = new EventDAO();
        try {
            List<EventDTO> list = dao.getListEvent("");
            if (!list.isEmpty()) {
                rs=list.size();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }
    
//    public static void main(String[] args) throws SQLException {
//        EventDAO dao = new EventDAO();
//        List<EventDTO> list = dao.getListEventByPage("", 1, 3);
//        for (EventDTO o : list) {
//            System.out.println(o.toString());
//        }
//    }
}
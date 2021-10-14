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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class EventDAO {
    
    public int getLastId() {
        //get the newest created id from tblEvents
        //SELECT MAX(eventId) FROM tblEvents
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int eventId = 0;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT MAX(eventId) as eventId FROM tblEvents";
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
            String sql = "SELECT eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName "
                    + "   FROM tblEvents e, tblStatusEvent s "
                    + "   WHERE title like ? AND s.statusId = e.statusId";
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
                Date startDate = rs.getDate("startDate");
                String startSlotId = rs.getString("startSlot");
                String endSlotId = rs.getString("endSlot");
                String status = rs.getString("statusName");

                UserDTO user = new UserDAO().getUserById(userId);
                SlotDTO startSlot = sDao.getSlotById(startSlotId);
                SlotDTO endSlot = sDao.getSlotById(endSlotId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                list.add(new EventDTO(eventId, user, title, description, location, createDatetime, startDate, startSlot, endSlot, status));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
    public List<EventDTO> getListFollowedEvent(String search, UserDTO user) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName "
                    + "   FROM tblEvents e, tblStatusEvent s, tblFollowedEvent f "
                    + "   WHERE title like ? AND s.statusId = e.statusId AND f.userId = ? AND f.follow = 1";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, user.getUserId());
            rs = stm.executeQuery();
            while (rs.next()) {
                SlotDAO sDao = new SlotDAO();
                int eventId = rs.getInt("eventId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String locationId = rs.getString("locationId");
                Date createDatetime = rs.getDate("createDatetime");
                Date startDate = rs.getDate("startDate");
                String startSlotId = rs.getString("startSlot");
                String endSlotId = rs.getString("endSlot");
                String status = rs.getString("statusName");

                UserDTO author = new UserDAO().getUserById(userId);
                SlotDTO startSlot = sDao.getSlotById(startSlotId);
                SlotDTO endSlot = sDao.getSlotById(endSlotId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                list.add(new EventDTO(eventId, author, title, description, location, createDatetime, startDate, startSlot, endSlot, status));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
    public void checkStatusEvent(List<EventDTO> list){
        Connection conn = null;
        ResultSet rs= null;
        PreparedStatement stm = null;
        LocalDate date = null;
        Time time = null;
        try {
            conn = DBConnection.getConnection();
            if(conn!=null){
                date = LocalDate.now();
                time = Time.valueOf(LocalTime.now());
                        list.forEach((_item) -> {
                            String sql = "UPDATE tblEvents "
                                    + "VALUES()"
                                    + "WHERE eventId = ?";
                });              
            }
        } catch (Exception e) {
        }
    }
    public List<EventDTO> getListOwnedEvent(UserDTO user) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName "
                    + "   FROM tblEvents e, tblStatusEvent s "
                    + "   WHERE userId = ? AND e.statusId = s.statusId";
            stm = conn.prepareStatement(sql);
            stm.setString(1, user.getUserId());
            rs = stm.executeQuery();
            while (rs.next()) {
                SlotDAO sDao = new SlotDAO();
                int eventId = rs.getInt("eventId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String locationId = rs.getString("locationId");
                Date createDatetime = rs.getDate("createDatetime");
                Date startDate = rs.getDate("startDate");
                String startSlotId = rs.getString("startSlot");
                String endSlotId = rs.getString("endSlot");
                String status = rs.getString("statusName");

                SlotDTO startSlot = sDao.getSlotById(startSlotId);
                SlotDTO endSlot = sDao.getSlotById(endSlotId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                list.add(new EventDTO(eventId, user, title, description, location, createDatetime, startDate, startSlot, endSlot, status));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListOwnedEvent: " + e.toString());
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
            if (search == null) {
                search = "";
            }
            conn = DBConnection.getConnection();
            String sql = "WITH tblEventPage AS (SELECT (ROW_NUMBER() over (order by startDate) ) AS RowNum,\n"
                    + "					eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName AS status \n"
                    + "				FROM tblEvents e, tblStatusEvent s \n"
                    + "				WHERE title like ? AND e.statusId = s.statusId)\n"
                    + "SELECT eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, status \n"
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
                Date startDate = rs.getDate("startDate");
                String startSlotId = rs.getString("startSlot");
                String endSlotId = rs.getString("endSlot");
                String status = rs.getString("status");

                UserDTO user = new UserDAO().getUserById(userId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                SlotDTO startSlot = new SlotDAO().getSlotById(startSlotId);
                SlotDTO endSlot = new SlotDAO().getSlotById(endSlotId);

                list.add(new EventDTO(eventId, user, title, description, location, createDatetime, startDate, startSlot, endSlot, status));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEventByPage: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
    public List<EventDTO> getFollowedEventByPage(UserDTO user, String search, int index, int pageSize) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "WITH tblEventPage AS (SELECT (ROW_NUMBER() over (order by startDate) ) AS RowNum,\n"
                    + "					e.eventId, e.userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName AS status \n"
                    + "				FROM tblEvents e, tblStatusEvent s, tblFollowedEvent f \n"
                    + "				WHERE title like ? AND e.statusId = s.statusId  AND f.userId = ? AND f.eventId = e.eventId AND f.follow = 1)\n"
                    + "SELECT eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, status \n"
                    + "FROM tblEventPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*? ";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, user.getUserId());
            //index*pageSize - (pageSize-1) AND index*pageSize
            stm.setInt(3, index);
            stm.setInt(4, pageSize);
            stm.setInt(5, pageSize);
            stm.setInt(6, index);
            stm.setInt(7, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                int eventId = rs.getInt("eventId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String locationId = rs.getString("locationId");
                Date createDatetime = rs.getDate("createDatetime");
                Date startDate = rs.getDate("startDate");
                String startSlotId = rs.getString("startSlot");
                String endSlotId = rs.getString("endSlot");
                String status = rs.getString("status");

                UserDTO author = new UserDAO().getUserById(userId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                SlotDTO startSlot = new SlotDAO().getSlotById(startSlotId);
                SlotDTO endSlot = new SlotDAO().getSlotById(endSlotId);

                list.add(new EventDTO(eventId, author, title, description, location, createDatetime, startDate, startSlot, endSlot, status));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getFollowedEventsByPage: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
    public List<EventDTO> getListEventByPageByOwner(UserDTO user, String search, int index, int pageSize) throws SQLException {
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "WITH tblEventPage AS (SELECT (ROW_NUMBER() over (order by startDate) ) AS RowNum,\n"
                    + "					eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName AS status \n"
                    + "				FROM tblEvents e, tblStatusEvent s \n"
                    + "				WHERE userId = ? AND title like ? AND e.statusId = s.statusId)\n"
                    + "SELECT eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, status \n"
                    + "FROM tblEventPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*?";
            stm = conn.prepareStatement(sql);
            if (search == null) {
                search = "";
            }
            stm.setString(1, user.getUserId());
            stm.setString(2, "%" + search + "%");
            //index*pageSize - (pageSize-1) AND index*pageSize
            stm.setInt(3, index);
            stm.setInt(4, pageSize);
            stm.setInt(5, pageSize);
            stm.setInt(6, index);
            stm.setInt(7, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                int eventId = rs.getInt("eventId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String locationId = rs.getString("locationId");
                Date createDatetime = rs.getDate("createDatetime");
                Date startDate = rs.getDate("startDate");
                String startSlotId = rs.getString("startSlot");
                String endSlotId = rs.getString("endSlot");
                String status = rs.getString("status");

                LocationDTO location = new LocationDAO().getLocationById(locationId);
                SlotDTO startSlot = new SlotDAO().getSlotById(startSlotId);
                SlotDTO endSlot = new SlotDAO().getSlotById(endSlotId);

                list.add(new EventDTO(eventId, user, title, description, location, createDatetime, startDate, startSlot, endSlot, status));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEventByPage: " + e.toString());
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
            conn = Utils.DBConnection.getConnection();
            String sql = "INSERT INTO tblEvents (userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, statusId) "
                    + "VALUES (?,?,?,?,?,?,?,?, (SELECT statusId FROM tblStatusEvent WHERE statusName = ?))";
            stm = conn.prepareStatement(sql);
            String userId = newEvent.getUser().getUserId();
            String title = newEvent.getTitle();
            String description = newEvent.getDescription();
            String locationId = newEvent.getLocation().getLocationId();
            Date createDatetime = newEvent.getCreateDatetime();
            Date startDate = newEvent.getStartDatetime();
            String startSlotId = newEvent.getStartSlot().getSlotId();
            String endSlotId = newEvent.getEndSlot().getSlotId();
            stm.setString(1, userId);
            stm.setString(2, title);
            stm.setString(3, description);
            stm.setString(4, locationId);
            stm.setDate(5, createDatetime);
            stm.setDate(6, startDate);
            stm.setString(7, startSlotId);
            stm.setString(8, endSlotId);
            stm.setString(9, "Pending");
            flag = stm.executeUpdate() > 0;
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
            if (newEvent.getUser() == null) {
                check = false;
            } else {
                conn = Utils.DBConnection.getConnection();
                String sql = "UPDATE tblEvents SET title=?, description=?, locationId=?, startDate=?, startSlot=?, endSlot=?, statusId = (SELECT statusId FROM tblStatusEvent WHERE statusName = ?) "
                        + "WHERE eventId=?";
                stm = conn.prepareStatement(sql);
                int eventId = newEvent.getEventId();
                String title = newEvent.getTitle();
                String description = newEvent.getDescription();
                String locationId = newEvent.getLocation().getLocationId();
                Date startDate = newEvent.getStartDatetime();
                String startSlotId = newEvent.getStartSlot().getSlotId();
                String endSlotId = newEvent.getEndSlot().getSlotId();
                String status = newEvent.getStatus();

                stm.setString(1, title);
                stm.setString(2, description);
                stm.setString(3, locationId);
                stm.setDate(4, startDate);
                stm.setString(5, startSlotId);
                stm.setString(6, endSlotId);
                stm.setString(7, status);
                stm.setInt(8, eventId);
                check = stm.executeUpdate() > 0;
            }
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
            conn = Utils.DBConnection.getConnection();
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

    public int countListEvent() {
        int rs = 0;
        EventDAO dao = new EventDAO();
        try {
            List<EventDTO> list = dao.getListEvent("");
            if (!list.isEmpty()) {
                rs = list.size();
            }
        } catch (SQLException ex) {
            log("Error at EventDAO - countListEvent: " + ex.toString());
        }
        return rs;
    }
    public int countListOwnedEvent(UserDTO user) {
        int rs = 0;
        EventDAO dao = new EventDAO();
        try {
            List<EventDTO> list = dao.getListOwnedEvent(user);
            if (!list.isEmpty()) {
                rs = list.size();
            }
        } catch (SQLException ex) {
            log("Error at EventDAO - countListOwnedEvent: " + ex.toString());
        }
        return rs;
    }

    public EventDTO getEventById(int eventId) {
        EventDTO event = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName "
                    + "   FROM tblEvents e, tblStatusEvent s "
                    + "   WHERE eventId = ? AND s.statusId = e.statusId";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, eventId);
            rs = stm.executeQuery();
            if (rs.next()) {
                SlotDAO sDao = new SlotDAO();
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String locationId = rs.getString("locationId");
                Date createDatetime = rs.getDate("createDatetime");
                Date startDate = rs.getDate("startDate");
                String startSlotId = rs.getString("startSlot");
                String endSlotId = rs.getString("endSlot");
                String status = rs.getString("statusName");

                UserDTO user = new UserDAO().getUserById(userId);
                SlotDTO startSlot = sDao.getSlotById(startSlotId);
                SlotDTO endSlot = sDao.getSlotById(endSlotId);
                LocationDTO location = new LocationDAO().getLocationById(locationId);
                event = new EventDTO(eventId, user, title, description, location, createDatetime, startDate, startSlot, endSlot, status);
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return event;
    }

    public static void main(String[] args) {
        EventDAO dao = new EventDAO();
        EventDTO event = dao.getEventById(2);
        System.out.println(event.toString());
        try {
            List<EventDTO> list = dao.getListEvent("");
            for (EventDTO o : list) {
                System.out.println(o.toString());
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}

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
public class FollowedEventDAO {

    public int getFollowing(EventDTO event) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT COUNT(eventId) as count "
                    + "   FROM tblFollowedEvent "
                    + "   WHERE eventId = ? AND follow = 1";
            stm = conn.prepareStatement(sql);
            int eventId = event.getEventId();
            stm.setInt(1, eventId);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count");
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return result;
    }

    private boolean initFollow(UserDTO user, EventDTO event) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO tblFollowedEvent(userId, eventId, follow) "
                    + "   VALUES(?, ?, ?)";
            stm = conn.prepareStatement(sql);
            String userId = user.getUserId();
            int eventId = event.getEventId();
            stm.setString(1, userId);
            stm.setInt(2, eventId);
            stm.setInt(3, 1);
            check = stm.executeUpdate() > 0;

        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }

    public int checkFollow(UserDTO user, EventDTO event) {
        Connection conn = null;
        PreparedStatement stm = null;
        int result = -1;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT follow "
                    + "FROM tblFollowedEvent "
                    + "WHERE userId= ? AND eventId = ?";
            stm = conn.prepareStatement(sql);
            String userId = user.getUserId();
            int eventId = event.getEventId();
            stm.setString(1, userId);
            stm.setInt(2, eventId);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("follow");
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return result;
    }

    private boolean changeFollow(UserDTO user, EventDTO event, int follow) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE tblFollowedEvent "
                    + "SET follow = ? "
                    + "WHERE userId=? AND eventId=?";
            stm = conn.prepareStatement(sql);

            String userId = user.getUserId();
            int eventId = event.getEventId();

            stm.setInt(1, follow == 1 ? 0 : 1);
            stm.setString(2, userId);
            stm.setInt(3, eventId);
            check = stm.executeUpdate() > 0;

        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }

    public boolean followEvent(UserDTO user, EventDTO event) {
        boolean check;
        int follow = checkFollow(user, event);
        if (follow == -1) {
            check = initFollow(user, event);
        } else {
            check = changeFollow(user, event, follow);
        }
        return check;
    }
    public List<EventDTO> getFollowedEvents(UserDTO user){
        List<EventDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName "
                    + "   FROM tblEvents e, tblStatusEvent s, tblFollowedEvent f "
                    + "   WHERE userId = ? AND e.statusId = s.statusId AND f.follow = 1 AND f.eventId = e.eventId";
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
            log("Error at FollowedEventDAO - getFollowedEvents: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
}

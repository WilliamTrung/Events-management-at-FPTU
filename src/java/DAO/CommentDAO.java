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
public class CommentDAO {
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
}

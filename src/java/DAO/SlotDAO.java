/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.SlotDTO;
import Utils.DBConnection;
import static java.rmi.server.LogStream.log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WilliamTrung
 */
public class SlotDAO {
    public List<SlotDTO> getListSlots(){
        List<SlotDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if(conn!=null){
                String sql = "SELECT slotId, startTime, endTime "
                        + "FROM tblSlots ";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    String slotId = rs.getString("slotId");
                    Time startTime = rs.getTime("startTime");
                    Time endTime = rs.getTime("endTime");
                    SlotDTO slot = new SlotDTO(slotId, startTime, endTime);
                    list.add(slot);
                }
            }
        } catch (Exception e) {
            log("Error at SlotDAO - getListSlots: "+ e.toString());
        }
        return list;
    }
    public SlotDTO getSlotById(String slotId){
        SlotDTO slot = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if(conn!=null){
                String sql = "SELECT startTime, endTime "
                        + "FROM tblSlots "
                        + "WHERE slotId like ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%"+slotId+"%");
                rs = stm.executeQuery();
                while(rs.next()){
                    Time startTime = rs.getTime("startTime");
                    Time endTime = rs.getTime("endTime");
                    slot = new SlotDTO(slotId, startTime, endTime);
                }
            }
        } catch (Exception e) {
            log("Error at SlotDAO - getSlotById: "+ e.toString());
        }
        return slot;
    }
    public SlotDTO getSlot(List<SlotDTO> list, String slotId){
        SlotDTO slot = null;
        for (SlotDTO slotDTO : list) {
            if (slotDTO.getSlotId().equals(slotId)) {
                slot = slotDTO;
                break;
            }
        }
        return slot;
    }
}

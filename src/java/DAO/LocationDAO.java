/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.LocationDTO;
import Utils.DBConnection;
import static java.rmi.server.LogStream.log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author WilliamTrung
 */
public class LocationDAO {
    
    public List<LocationDTO> getListLocations(String search){
        List<LocationDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if(conn!=null){
                String sql = "SELECT locationId, locationName, seat "
                        + "FROM tblLocations "
                        + "WHERE locationName like ? "
                        + "ORDER BY locationName ASC";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%"+search+"%");
                rs = stm.executeQuery();
                while(rs.next()){
                    String locationId = rs.getString("locationId");
                    String locationName = rs.getString("locationName");
                    int seat = rs.getInt("seat");
                    LocationDTO location = new LocationDTO(locationId, seat, locationName);
                    list.add(location);
                }
            }
        } catch (Exception e) {
            log("Error at LocationDAO - getListLocations: "+ e.toString());
        }
        return list;
    }
    public LocationDTO getLocationById(String search){
        LocationDTO location = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if(conn!=null){
                String sql = "SELECT locationName, seat "
                        + "FROM tblLocations "
                        + "WHERE locationId = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, search);
                rs = stm.executeQuery();
                if(rs.next()){
                    String locationId = search;
                    String locationName = rs.getString("locationName");
                    int seat = rs.getInt("seat");
                    location = new LocationDTO(locationId, seat, locationName);
                }
            }
        } catch (Exception e) {
            log("Error at LocationDAO - getLocationById: "+ e.toString());
        }
        return location;
    }
}

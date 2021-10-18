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
    public boolean insertLocation(LocationDTO location){
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO tblLocations(locationName, seat) "
                    + "VALUES (?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, location.getLocationName());
            stm.setInt(2, location.getSeat());
            check = stm.executeUpdate() >0;
        } catch (Exception e) {
            log("Error at LocationDAO - insertLocation:" + e.toString());
        } 
        return check;
    }
    public boolean updateLocation(LocationDTO location){
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE tblLocations "
                    + "SET locationName = ?, seat = ? "
                    + "WHERE locationId = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, location.getLocationName());
            stm.setInt(2, location.getSeat());
            stm.setString(3, location.getLocationId());
            check = stm.executeUpdate() >0;
        } catch (Exception e) {
            log("Error at LocationDAO - updateLocation:" + e.toString());
        } 
        return check;
    }
    public boolean removeLocation(LocationDTO location){
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM tblLocations "
                    + "WHERE locationId = ?";
            stm = conn.prepareStatement(sql);;
            stm.setString(1, location.getLocationId());
            check = stm.executeUpdate() >0;
        } catch (Exception e) {
            log("Error at LocationDAO - removeLocation:" + e.toString());
        } 
        return check;
    }
    public int getLocationEndPage(){
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn = DBConnection.getConnection();
            if(conn!=null){
                String sql = "SELECT COUNT(locationId) as amount "
                        + " FROM tblLocations ";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("amount");
                }
            }
        } catch (Exception e) {
            log("Error at LocationDAO - getLocationEndPage: "+ e.toString());
        }
        return result;
    }
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
    public List<LocationDTO> getListLocationByPage(String search, int index, int pageSize){
        List<LocationDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            if (search == null) {
                search = "";
            }
            conn = DBConnection.getConnection();
            String sql = "WITH tblLocationPage AS (SELECT (ROW_NUMBER() over (order by locationName) ) AS RowNum,\n"
                    + "					locationId, seat, locationName \n"
                    + "				FROM tblLocations \n"
                    + "				WHERE locationName like ?)\n"
                    + "SELECT locationId, seat, locationName \n"
                    + "FROM tblLocationPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*?";
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
                String locationName = rs.getString("locationName");
                String locationId = rs.getString("locationId");
                int seat = rs.getInt("seat");
                

                list.add(new LocationDTO(locationId, seat, locationName));
            }
        } catch (Exception e) {
            log("Error at LocationDAO - getListLocationByPage: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
}

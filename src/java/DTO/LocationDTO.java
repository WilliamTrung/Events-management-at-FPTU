/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author WilliamTrung
 */
public class LocationDTO implements Comparable<LocationDTO> {
    private String locationId;
    private int seat;
    private String locationName;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LocationDTO(String locationId, int seat, String locationName) {
        this.locationId = locationId;
        this.seat = seat;
        this.locationName = locationName;
    }

    @Override
    public int compareTo(LocationDTO o) {
        int result = this.locationName.compareTo(o.locationName);
        return result;
    }
    
    
}

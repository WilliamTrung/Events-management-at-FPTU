/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.sql.Date;



/**
 *
 * @author Admin
 */
public class EventDTO {
    private int eventId;
    private UserDTO user;
    private String title;
    private String description;
    private LocationDTO location;
    private Date createDatetime;
    private SlotDTO slot;
    private String status;

    public EventDTO(int eventId, UserDTO user, String title, String description, LocationDTO location, Date createDatetime, SlotDTO slot, String status) {
        this.eventId = eventId;
        this.user = user;
        this.title = title;
        this.description = description;
        this.location = location;
        this.createDatetime = createDatetime;
        this.slot = slot;
        this.status = status;
    }

    public int getEventId() {
        return eventId;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public SlotDTO getSlot() {
        return slot;
    }

    public String getStatus() {
        return status;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public void setSlot(SlotDTO slot) {
        this.slot = slot;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    
}
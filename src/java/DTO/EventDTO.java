/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class EventDTO implements Serializable{

    private int eventId;
    private UserDTO user;
    private String title;
    private String description;
    private LocationDTO location;
    private Date createDatetime;
    private Date startDatetime;
    private SlotDTO startSlot;
    private SlotDTO endSlot;
    private String status;

    public EventDTO() {
    }

    public EventDTO(int eventId, UserDTO user, String title, String description, LocationDTO location, Date createDatetime, Date startDatetime, SlotDTO startSlot, SlotDTO endSlot, String status) {
        this.eventId = eventId;
        this.user = user;
        this.title = title;
        this.description = description;
        this.location = location;
        this.createDatetime = createDatetime;
        this.startDatetime = startDatetime;
        this.startSlot = startSlot;
        this.endSlot = endSlot;
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

    public Date getStartDatetime() {
        return startDatetime;
    }

    public SlotDTO getStartSlot() {
        return startSlot;
    }

    public SlotDTO getEndSlot() {
        return endSlot;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "EventDTO{" + "eventId=" + eventId + ", user=" + user + ", title=" + title + ", description=" + description + ", location=" + location + ", createDatetime=" + createDatetime + ", startDatetime=" + startDatetime + ", startSlot=" + startSlot + ", endSlot=" + endSlot + ", status=" + status + '}';
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

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public void setStartSlot(SlotDTO startSlot) {
        this.startSlot = startSlot;
    }

    public void setEndSlot(SlotDTO endSlot) {
        this.endSlot = endSlot;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

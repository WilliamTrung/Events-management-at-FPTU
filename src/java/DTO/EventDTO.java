/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Admin
 */
public class EventDTO {
    private String eventId;
    private String userId;
    private String title;
    private String description;
    private String location;
    private String createDatetime;
    private String startDatetime;
    private String endDatetime;
    private String statusId;

    public EventDTO(String eventId, String userId, String title, String description, String location, String createDatetime, String startDatetime, String endDatetime, String statusId) {
        this.eventId = eventId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.createDatetime = createDatetime;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.statusId = statusId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public EventDTO() {
    }

}
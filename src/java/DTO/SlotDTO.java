/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.sql.Time;
/**
 *
 * @author WilliamTrung
 */
public class SlotDTO {
    private String slotId;
    private Time startTime;
    private Time endTime;

    public String getSlotId() {
        return slotId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public SlotDTO(String slotId, Time startTime, Time endTime) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
    }


     
    
}

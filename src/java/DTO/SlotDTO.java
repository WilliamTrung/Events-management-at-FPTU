/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.sql.Time;
import java.util.Formatter;
/**
 *
 * @author WilliamTrung
 */
public class SlotDTO implements Serializable{
    private final String slotId;
    private final Time startTime;
    private final Time endTime;

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
    public String getStart(){
        Formatter fmt = new Formatter();
        fmt.format("%tl:%tM", startTime, startTime);
        return fmt.toString();
    }
    public String getEnd(){
        Formatter fmt = new Formatter();
        fmt.format("%tl:%tM", endTime, endTime);
        return fmt.toString();
    }

     
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extension;

import DAO.SlotDAO;
import DTO.SlotDTO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author WilliamTrung
 */
public class AI {
    String id;
    String message;

    public AI(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
    
    
    public static List<SlotDTO> checkChosenSlot(String[] uri, List<SlotDTO> slots) {
        List<SlotDTO> list = new ArrayList<>();
        Calendar c = new Calendar();
        Date preDay = c.convertToDate(uri[0].split("-")[1]);
        Date nextDay = Date.valueOf(LocalDate.now());
        if (!preDay.before(nextDay)) {
            SlotDTO startSlot = null;
            SlotDTO endSlot = null;
            boolean check = true;
            for (String str : uri) {
                nextDay = c.convertToDate(str.split("-")[1]);
                if (!preDay.equals(nextDay)) {
                    check = false;
                    break;
                }
            }
            if (check) {
                startSlot = new SlotDAO().getSlot(slots, uri[0].split("-")[0]);
                endSlot = new SlotDAO().getSlot(slots, uri[uri.length - 1].split("-")[0]);
                list.add(startSlot);
                list.add(endSlot);
            }
        } else {
            list = null;
        }
        return list;
    }
    public static List<SlotDTO> checkChosenUpdateSlot(String[] uri, List<SlotDTO> slots) {
        List<SlotDTO> list = new ArrayList<>();
        /*
        int index = uri.indexOf("-");
        int length = uri.length()-1;
        String temp = uri.substring(index+1, length+1);
        Date preDay = Date.valueOf(temp);
*/
        Date preDay = Date.valueOf(uri[0].substring(uri[0].indexOf("-")+1, uri[0].length()));
        Date nextDay = Date.valueOf(LocalDate.now());
        if (!preDay.before(nextDay)) {
            SlotDTO startSlot = null;
            SlotDTO endSlot = null;
            boolean check = true;
            for (String str : uri) {
                nextDay = Date.valueOf(str.substring(str.indexOf("-")+1, str.length()));
                if (!preDay.equals(nextDay)) {
                    check = false;
                    break;
                }
            }
            if (check) {
                startSlot = new SlotDAO().getSlot(slots, uri[0].split("-")[0]);
                endSlot = new SlotDAO().getSlot(slots, uri[uri.length - 1].split("-")[0]);
                list.add(startSlot);
                list.add(endSlot);
            }
        } else {
            list = null;
        }
        return list;
    }
}

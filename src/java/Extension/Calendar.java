/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extension;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WilliamTrung
 */
public class Calendar {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd MMMM yyyy");
    private List<String> splitDay(List<String> list){
        String replace = "</br>";
        List<String> days = new ArrayList<>();
        list.stream().map((day) -> day.replaceFirst(" ", replace)).forEachOrdered((day) -> {
            days.add(day);
        });
        return days;
    }
    public  List<String> getWeek(long week) {
        List<String> listDays = new ArrayList<>();
        List<LocalDate>list = getDaysOfWeek(week);
        for (LocalDate localDate : list) {
            String day = localDate.format(formatter);
            listDays.add(day);
        }
        listDays = splitDay(listDays);
        return listDays;
    }
    private List<LocalDate> getDaysOfWeek(long week) {
        List<LocalDate> list;
        list = new ArrayList<>();
        if (week == 0) {
            week = 1 / 7;
        }
        LocalDate day = LocalDate.now().plusDays(week * 7);
        while(day.getDayOfWeek() != DayOfWeek.MONDAY){
            day = day.minusDays(1);
        }
        //now day is at the first day of week (MONDAY)
        list.add(day);
        for (int i = 0; i < 5; i++) {
            day =day.plusDays(1);
            list.add(day);
        }
        return list;
    }
    public Date convertToDate(String day){
        //only use with day = "Mon <br> dd MMMM yyyy"
        String replace = "</br>";
        day = day.replaceFirst(replace, " ");
        LocalDate datetime = LocalDate.parse(day, formatter);
        Date date = Date.valueOf(datetime);
        return date;
    }
}

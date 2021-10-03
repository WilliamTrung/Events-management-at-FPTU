/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extension;

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
    public static List<String> getWeek(long week) {
        List<String> listDays = new ArrayList<>();
        List<LocalDate>list = new Calendar().getDaysOfWeek(week);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM");
        list.stream().map((localDate) -> localDate.format(formatter)).forEachOrdered((day) -> {
            listDays.add(day);
        });
        return listDays;
    }

    private List<LocalDate> getDaysOfWeek(long week) {
        List<LocalDate> list;
        list = new ArrayList<>();
        if (week == 0) {
            week = 1 / 7;
        }
        LocalDate day = LocalDate.now().plusDays(week * 7);
        while (day.getDayOfWeek() != DayOfWeek.SUNDAY) {
            day.minusDays(1);
        }
        //now day is at the first day of week (Sunday)
        for (int i = 0; i < 6; i++) {
            day = day.plusDays(1);
            list.add(day);
        }
        return list;
    }
}

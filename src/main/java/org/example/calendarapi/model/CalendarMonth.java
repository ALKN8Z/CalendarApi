package org.example.calendarapi.model;

import lombok.Data;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public class CalendarMonth {
    private int year;
    private String month;
    private List<List<Integer>> weeks;

    public CalendarMonth(YearMonth yearMonth) {
        this.year = yearMonth.getYear();
        this.month = yearMonth.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru"));
        this.weeks = initializeWeeks(yearMonth);
    }

    private static List<List<Integer>> initializeWeeks(YearMonth yearMonth) {
        List<List<Integer>> weeks = new ArrayList<>();
        int daysInMonth = yearMonth.lengthOfMonth();
        int firstDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue();

        List<Integer> currentWeek = new ArrayList<>();

        for (int i = 1; i < firstDayOfWeek; i++) {
            currentWeek.add(null);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            currentWeek.add(day);
            if (currentWeek.size() == 7) {
                weeks.add(currentWeek);
                currentWeek = new ArrayList<>();
            }
        }

        while (currentWeek.size() < 7) {
            currentWeek.add(null);
        }
        weeks.add(currentWeek);

        return weeks;
    }
}

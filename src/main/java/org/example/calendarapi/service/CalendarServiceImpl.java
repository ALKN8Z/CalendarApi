package org.example.calendarapi.service;

import org.example.calendarapi.model.CalendarMonth;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Override
    public List<CalendarMonth> getCalendarForYear(int year) {
        List<CalendarMonth> calendarMonths = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            calendarMonths.add(new CalendarMonth(java.time.YearMonth.of(year, month)));
        }
        return calendarMonths;
    }

    @Override
    public int getUniqueCalendarCount() {
        return 14;
    }
}

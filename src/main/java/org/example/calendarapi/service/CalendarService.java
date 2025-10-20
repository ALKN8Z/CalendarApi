package org.example.calendarapi.service;

import org.example.calendarapi.model.CalendarMonth;

import java.util.List;

public interface CalendarService {
    List<CalendarMonth> getCalendarForYear(int year);
    int getUniqueCalendarCount();
}

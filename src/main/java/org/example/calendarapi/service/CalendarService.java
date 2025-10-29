package org.example.calendarapi.service;

import org.example.calendarapi.entity.CalendarMonth;
import org.example.calendarapi.entity.CalendarYear;

import java.util.List;

public interface CalendarService {
    CalendarYear getOrGenerateCalendarYear(int year);
    CalendarYear generateCalendarYear(int year);
    int getUniqueCalendarCount();
}

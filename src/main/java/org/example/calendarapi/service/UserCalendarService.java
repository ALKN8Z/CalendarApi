package org.example.calendarapi.service;


import java.util.List;

public interface UserCalendarService {
    boolean userHasSavedCalendarYear(String username, int year);
    List<Integer> getUserSavedCalendarYears(String username);
    List<String> getAllUsernames();
    void linkUserToCalendarYear(String username, int year);
}

package org.example.calendarapi.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarapi.entity.CalendarYear;
import org.example.calendarapi.entity.UserCalendarLink;
import org.example.calendarapi.exception.NotFoundException;
import org.example.calendarapi.repository.CalendarYearRepository;
import org.example.calendarapi.repository.UserCalendarLinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCalendarServiceImpl implements UserCalendarService {

    private final CalendarYearRepository calendarYearRepository;

    private final UserCalendarLinkRepository userCalendarLinkRepository;

    @Override
    public boolean userHasSavedCalendarYear(String username, int year) {
        return userCalendarLinkRepository.existsByUsernameAndCalendarYear_Year(username, year);
    }

    @Override
    public List<Integer> getUserSavedCalendarYears(String username) {
        return userCalendarLinkRepository.findByUsername(username)
                .stream()
                .map(link -> link.getCalendarYear().getYear())
                .toList();
    }

    @Override
    public List<String> getAllUsernames() {
        return userCalendarLinkRepository.findDistinctUsernames();
    }

    @Override
    @Transactional
    public void linkUserToCalendarYear(String username, int year) {
        CalendarYear calendarYear = calendarYearRepository.findByYear(year)
                .orElseThrow(() -> new NotFoundException("Календарь не найден!"));


        if (!userHasSavedCalendarYear(username, year)) {
            UserCalendarLink userCalendarLink = new UserCalendarLink();
            userCalendarLink.setCalendarYear(calendarYear);
            userCalendarLink.setUsername(username);
            userCalendarLinkRepository.save(userCalendarLink);
        }
    }
}

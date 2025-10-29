package org.example.calendarapi.service;

import lombok.RequiredArgsConstructor;
import org.example.calendarapi.entity.CalendarDay;
import org.example.calendarapi.entity.CalendarMonth;
import org.example.calendarapi.entity.CalendarWeek;
import org.example.calendarapi.entity.CalendarYear;
import org.example.calendarapi.repository.CalendarYearRepository;
import org.example.calendarapi.repository.UserCalendarLinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarYearRepository calendarYearRepository;

    @Override
    @Transactional
    public CalendarYear getOrGenerateCalendarYear(int year) {
        return calendarYearRepository.findByYear(year)
                .orElseGet(() -> calendarYearRepository.save(generateCalendarYear(year)));
    }

    @Override
    public CalendarYear generateCalendarYear(int year) {
        CalendarYear calendarYear = new CalendarYear();
        calendarYear.setYear(year);

        for (int month = 1; month <= 12; month++) {
            CalendarMonth calendarMonth = new CalendarMonth();
            calendarMonth.setMonthNumber(month);
            calendarMonth.setYear(calendarYear);

            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate first = yearMonth.atDay(1);
                                    // количество дней в месяце + номер дня недели первого дня месяца - 1 деленное на 7 с округлением вверх
            int weeksInMonth = (int) Math.ceil((first.lengthOfMonth() + first.getDayOfWeek().getValue() - 1) / 7.0);

            for (int w = 1; w <= weeksInMonth; w++) {
                CalendarWeek calendarWeek = new CalendarWeek();
                calendarWeek.setWeekNumber(w);
                calendarWeek.setCalendarMonth(calendarMonth);

                //вычисление числа для первого дня недели, если получается отрицательно число значит этот день не относится к текущему месяцу
                int startDay = (w - 1) * 7 + 1 - first.getDayOfWeek().getValue() + 1;
                for (int d = 0; d < 7; d++) {
                    int currentDay = startDay + d;
                    if (currentDay >= 1 && currentDay <= yearMonth.lengthOfMonth()) {
                        LocalDate date = yearMonth.atDay(currentDay);
                        CalendarDay calendarDay = new CalendarDay();
                        calendarDay.setDayOfMonth(currentDay);
                        calendarDay.setDayOfWeek(date.getDayOfWeek().toString());
                        calendarDay.setWeekend(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
                        calendarDay.setCalendarWeek(calendarWeek);
                        calendarWeek.getDays().add(calendarDay);

                    }
                }
                calendarMonth.getWeeks().add(calendarWeek);
            }
            calendarYear.getMonths().add(calendarMonth);
        }
        return calendarYear;
    }


    @Override
    public int getUniqueCalendarCount() {
        return 14;
    }
}

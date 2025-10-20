package org.example.calendarapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.calendarapi.model.CalendarMonth;
import org.example.calendarapi.service.CalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping
    public String getHomePage() {
        return "calendar";
    }

    @PostMapping("/calendar")
    public String getCalendarPage(@RequestParam(name = "year") int year, Model model) {
        if (year < 1600) {
            model.addAttribute("error", "Введенный год меньше 1600");
        }

        List<CalendarMonth> calendarMonths = calendarService.getCalendarForYear(year);
        model.addAttribute("months", calendarMonths);
        model.addAttribute("year", year);
        model.addAttribute("uniqueCalendars", calendarService.getUniqueCalendarCount());
        return "calendar";
    }
}


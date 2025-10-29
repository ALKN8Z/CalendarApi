package org.example.calendarapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.calendarapi.entity.CalendarMonth;
import org.example.calendarapi.entity.CalendarYear;
import org.example.calendarapi.entity.YearPayload;
import org.example.calendarapi.service.CalendarService;
import org.example.calendarapi.service.UserCalendarService;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    private final UserCalendarService userCalendarService;

    @GetMapping
    public String getHomePage() {
        return "index";
    }

    @PostMapping("/generate")
    public String getCalendarPage(@Valid YearPayload yearPayload,
                                  BindingResult bindingResult,
                                  Model model,
                                  @AuthenticationPrincipal OidcUser principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorTitle", "Ошибка валидации");
            model.addAttribute("errorMessage", bindingResult.getFieldError("year").getDefaultMessage());
            return "index";
        }

        model.addAttribute("isSaved", userCalendarService.userHasSavedCalendarYear(principal.getPreferredUsername(), yearPayload.year()));
        CalendarYear calendarYear = calendarService.getOrGenerateCalendarYear(yearPayload.year());
        model.addAttribute("months", calendarYear.getMonths());
        model.addAttribute("year", calendarYear.getYear());
        model.addAttribute("uniqueCalendarsCount", calendarService.getUniqueCalendarCount());
        return "calendar-view";
    }

    @PostMapping("/save")
    public String saveCalendar(@Valid YearPayload yearPayload,
                               BindingResult bindingResult,
                               Model model,
                               @AuthenticationPrincipal OidcUser principal) {
        if (bindingResult.hasErrors()) {
            CalendarYear calendarYear = calendarService.getOrGenerateCalendarYear(yearPayload.year());
            model.addAttribute("months", calendarYear.getMonths());
            model.addAttribute("year", calendarYear.getYear());
            model.addAttribute("uniqueCalendarsCount", calendarService.getUniqueCalendarCount());
            model.addAttribute("errorTitle", "Ошибка валидации");
            model.addAttribute("isSaved", false);
            model.addAttribute("errorMessage", bindingResult.getFieldError("year").getDefaultMessage());
            return "calendar-view";
        }

        userCalendarService.linkUserToCalendarYear(principal.getPreferredUsername(), yearPayload.year());
        return "redirect:/calendar/saved";

    }

    @GetMapping("/saved")
    public String getSavedCalendarYears(@AuthenticationPrincipal OidcUser principal, Model model) {

        model.addAttribute("savedYears", userCalendarService.getUserSavedCalendarYears(principal.getPreferredUsername()));
        return "saved-calendars";
    }





}


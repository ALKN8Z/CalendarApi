package org.example.calendarapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.calendarapi.service.CalendarService;
import org.example.calendarapi.service.UserCalendarService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserCalendarService userCalendarService;

    @GetMapping("/users")
    public String viewUsers(Model model) {
        model.addAttribute("usernames", userCalendarService.getAllUsernames());
        return "admin-users-page";
    }

    @GetMapping("/users/{username}")
    public String viewUserCalendars(Model model, @PathVariable String username) {
        model.addAttribute("username", username);
        model.addAttribute("years", userCalendarService.getUserSavedCalendarYears(username));
        return "admin-user-saved-calendars";
    }
}

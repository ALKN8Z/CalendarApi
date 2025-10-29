package org.example.calendarapi.repository;

import org.example.calendarapi.entity.CalendarYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarYearRepository extends JpaRepository<CalendarYear, Integer> {
    Optional<CalendarYear> findByYear(int year);
}

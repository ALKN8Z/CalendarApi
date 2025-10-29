package org.example.calendarapi.repository;

import org.example.calendarapi.entity.UserCalendarLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCalendarLinkRepository extends JpaRepository<UserCalendarLink, Integer> {
    boolean existsByUsernameAndCalendarYear_Year(String username, int calendarYearYear);

    @Query("SELECT DISTINCT u.username FROM UserCalendarLink u")
    List<String> findDistinctUsernames();

    List<UserCalendarLink> findByUsername(String username);
}

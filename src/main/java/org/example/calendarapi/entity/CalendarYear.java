package org.example.calendarapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "calendar_year")
public class CalendarYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "year", nullable = false, unique = true)
    private int year;

    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CalendarMonth> months = new ArrayList<>();

    @OneToMany(mappedBy = "calendarYear", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserCalendarLink> userCalendarLinks = new ArrayList<>();
}

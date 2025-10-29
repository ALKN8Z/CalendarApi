package org.example.calendarapi.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "calendar_week")
public class CalendarWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "week_number", nullable = false)
    private int weekNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_month_id", referencedColumnName = "id", nullable = false)
    private CalendarMonth calendarMonth;

    @OneToMany(mappedBy = "calendarWeek", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CalendarDay> days = new ArrayList<>();

    @Transient
    public CalendarDay getDayAtPosition(int position) {
        for (CalendarDay d : days) {
            DayOfWeek dow = DayOfWeek.valueOf(d.getDayOfWeek());
            if (dow.getValue() == position) {
                return d;
            }
        }
        return null;
    }
}

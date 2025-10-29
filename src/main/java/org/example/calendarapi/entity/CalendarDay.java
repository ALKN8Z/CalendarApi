package org.example.calendarapi.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "calendar_day")
@NoArgsConstructor
public class CalendarDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "day_of_month", nullable = false)
    private int dayOfMonth;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

    @Column(name = "is_weekend", nullable = false)
    private boolean isWeekend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_week_id", referencedColumnName = "id", nullable = false)
    private CalendarWeek calendarWeek;
}

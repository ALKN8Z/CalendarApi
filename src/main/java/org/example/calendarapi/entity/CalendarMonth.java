package org.example.calendarapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "calendar_month")
public class CalendarMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "month_number", nullable = false)
    private int monthNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_year_id", referencedColumnName = "id", nullable = false)
    private CalendarYear year;

    @OneToMany(mappedBy = "calendarMonth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CalendarWeek> weeks = new ArrayList<>();

    @Transient
    public String getName(){
        return Month.of(monthNumber).getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"));
    }

}

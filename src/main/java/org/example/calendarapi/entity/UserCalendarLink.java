package org.example.calendarapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_calendar_links", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "calendar_year_id"})
})
public class UserCalendarLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_year_id", referencedColumnName = "id")
    private CalendarYear calendarYear;
}

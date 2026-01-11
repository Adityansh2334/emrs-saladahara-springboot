package org.labs.jkcloud.emrs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String date;
    private String month;
    private String type;

    @ManyToOne
    @JoinColumn(name = "academic_calendar_id")
    @JsonIgnore
    private AcademicCalendar academicCalendar;
}

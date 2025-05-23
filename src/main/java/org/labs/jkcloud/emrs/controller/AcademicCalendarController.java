package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.AcademicCalendar;
import org.labs.jkcloud.emrs.service.AcademicCalendarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-calendar")
@RequiredArgsConstructor
public class AcademicCalendarController {

    private final AcademicCalendarService academicCalendarService;

    @GetMapping
    public AcademicCalendar getAcademicCalendar() {
        return academicCalendarService.getAcademicCalendar();
    }

    @PostMapping
    public AcademicCalendar saveAcademicCalendar(@RequestBody AcademicCalendar academicCalendar) {
        return academicCalendarService.saveAcademicCalendar(academicCalendar);
    }

    @DeleteMapping("/{id}")
    public void deleteAcademicCalendar(@PathVariable Long id) {
        academicCalendarService.deleteAcademicCalendar(id);
    }

    @GetMapping("/{id}")
    public AcademicCalendar getAcademicCalendarById(@PathVariable Long id) {
        return academicCalendarService.getAcademicCalendarById(id);
    }

}

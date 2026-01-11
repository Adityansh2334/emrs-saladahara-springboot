package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.exception.ResourceNotFoundException;
import org.labs.jkcloud.emrs.model.AcademicCalendar;
import org.labs.jkcloud.emrs.model.Events;
import org.labs.jkcloud.emrs.repository.AcademicCalendarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicCalendarService {

    private final AcademicCalendarRepository repo;

    public AcademicCalendar saveAcademicCalendar(AcademicCalendar academicCalendar) {
        if (academicCalendar.getEvents() != null) {
            for (Events event : academicCalendar.getEvents()) {
                event.setAcademicCalendar(academicCalendar); // Set reverse link
            }
        }
        return repo.save(academicCalendar);
    }

    public AcademicCalendar getAcademicCalendar() {
        List<AcademicCalendar> academicCalendars = repo.findAll();
        if (academicCalendars.isEmpty()) {
            throw new ResourceNotFoundException("Academic Calendar not found");
        }
        return academicCalendars.get(0);
    }

    public AcademicCalendar getAcademicCalendarById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Academic Calendar not found with id " + id));
    }

    public void deleteAcademicCalendar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Academic Calendar not found with id " + id);
        }
        repo.deleteById(id);
    }

}

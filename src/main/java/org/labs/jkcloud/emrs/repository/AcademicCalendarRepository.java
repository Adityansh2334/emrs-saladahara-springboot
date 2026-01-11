package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, Long> {

}

package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.UpcomingExams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpcomingExamsRepository extends JpaRepository<UpcomingExams, Long> {
}

package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.LectureNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureNotesRepository extends JpaRepository<LectureNotes, Long> {
}

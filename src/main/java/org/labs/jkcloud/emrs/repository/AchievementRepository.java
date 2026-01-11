package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findBySection(String section);
}

package org.labs.jkcloud.emrs.repository;


import org.labs.jkcloud.emrs.model.HallOfAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallOfAdminRepository extends JpaRepository<HallOfAdmin, Long> {
}

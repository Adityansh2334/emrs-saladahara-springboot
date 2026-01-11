package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenderRepository extends JpaRepository<Tender, Long> {
}

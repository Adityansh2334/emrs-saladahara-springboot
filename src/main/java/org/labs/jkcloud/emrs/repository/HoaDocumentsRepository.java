package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.HoaDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDocumentsRepository extends JpaRepository<HoaDocuments, Long> {
}

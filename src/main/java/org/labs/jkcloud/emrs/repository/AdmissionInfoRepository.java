package org.labs.jkcloud.emrs.repository;

import org.labs.jkcloud.emrs.model.AdmissionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionInfoRepository extends JpaRepository<AdmissionInfo, Long> {

}

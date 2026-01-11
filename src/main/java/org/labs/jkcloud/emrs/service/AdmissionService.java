package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.AdmissionInfo;
import org.labs.jkcloud.emrs.repository.AdmissionInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final AdmissionInfoRepository admissionRepo;

    public AdmissionInfo getAdmissionInfo() {
        return admissionRepo.findAll().stream().findFirst().orElse(null);
    }

    public AdmissionInfo saveOrUpdate(AdmissionInfo admission) {
        // Ensuring bidirectional mapping is properly set
        admission.getEligibility().forEach(e -> e.setAdmission(admission));
        admission.getDocumentsRequired().forEach(d -> d.setAdmission(admission));
        admission.getImportantDates().forEach(i -> i.setAdmission(admission));
        return admissionRepo.save(admission);
    }

    public AdmissionInfo getAdmissionInfo(Long id) {
        return admissionRepo.findById(id).orElse(null);
    }
}


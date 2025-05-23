package org.labs.jkcloud.emrs.controller;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.AdmissionInfo;
import org.labs.jkcloud.emrs.model.EligibilityCriteria;
import org.labs.jkcloud.emrs.model.ImportantDate;
import org.labs.jkcloud.emrs.model.RequiredDocument;
import org.labs.jkcloud.emrs.model.VO.AdmissionRequestResponse;
import org.labs.jkcloud.emrs.service.AdmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admissions")

@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;

    @GetMapping
    public ResponseEntity<AdmissionRequestResponse> getAdmission() {
        AdmissionInfo info = admissionService.getAdmissionInfo();
        if (info == null) {
            return ResponseEntity.notFound().build();
        }
        AdmissionRequestResponse dto = (AdmissionRequestResponse) dto(info,null);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AdmissionRequestResponse> saveOrUpdate(@RequestBody AdmissionRequestResponse info) {
        AdmissionInfo saved = admissionService.saveOrUpdate((AdmissionInfo) dto(null, info));

        return ResponseEntity.ok((AdmissionRequestResponse) dto(saved, null));
    }

    @GetMapping("{id}")
    public ResponseEntity<AdmissionRequestResponse> getAdmission(@PathVariable Long id) {
        AdmissionInfo info = admissionService.getAdmissionInfo(id);
        if(info == null) return ResponseEntity.notFound().build();
        AdmissionRequestResponse dto = (AdmissionRequestResponse) dto(info,null);
        return ResponseEntity.ok(dto);
    }

    private Object dto(AdmissionInfo infoFrom, AdmissionRequestResponse dtoFrom) {
        if (Objects.isNull(dtoFrom)) {
            // Convert Entity -> DTO
            AdmissionRequestResponse dto = new AdmissionRequestResponse();
            dto.setId(infoFrom.getId());
            dto.setApplicationFileUrl(infoFrom.getApplicationFormUrl());
            dto.setContact(infoFrom.getContact());

            dto.setEligibility(infoFrom.getEligibility().stream()
                    .map(EligibilityCriteria::getText)
                    .collect(Collectors.toList()));

            dto.setDocumentsRequired(infoFrom.getDocumentsRequired().stream()
                    .map(RequiredDocument::getDocumentName)
                    .collect(Collectors.toList()));

            dto.setImportantDates(
                    infoFrom.getImportantDates().stream()
                            .map(d -> ImportantDate.builder()
                                    .event(d.getEvent())
                                    .date(d.getDate())
                                    .build())
                            .collect(Collectors.toList())
            );

            return dto;
        } else {
            // Convert DTO -> Entity
            AdmissionInfo info = new AdmissionInfo();
            info.setId(dtoFrom.getId());
            info.setApplicationFormUrl(dtoFrom.getApplicationFileUrl());
            info.setContact(dtoFrom.getContact());

            info.setEligibility(dtoFrom.getEligibility().stream()
                    .map(text -> EligibilityCriteria.builder().text(text).build())
                    .collect(Collectors.toList()));

            info.setDocumentsRequired(dtoFrom.getDocumentsRequired().stream()
                    .map(name -> RequiredDocument.builder().documentName(name).build())
                    .collect(Collectors.toList()));

            info.setImportantDates(
                    dtoFrom.getImportantDates().stream()
                            .map(d -> ImportantDate.builder()
                                    .event(d.getEvent())
                                    .date(d.getDate())
                                    .build())
                            .collect(Collectors.toList())
            );
            return info;
        }
    }
}
package org.labs.jkcloud.emrs.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicationFormUrl;

    @OneToOne(cascade = CascadeType.ALL)
    private Contact contact;

    @OneToMany(mappedBy = "admission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EligibilityCriteria> eligibility = new ArrayList<>();

    @OneToMany(mappedBy = "admission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequiredDocument> documentsRequired = new ArrayList<>();

    @OneToMany(mappedBy = "admission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImportantDate> importantDates = new ArrayList<>();


}

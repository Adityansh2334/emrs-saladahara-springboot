package org.labs.jkcloud.emrs.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequiredDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentName;

    @ManyToOne
    @JoinColumn(name = "admission_id")
    private AdmissionInfo admission;

}
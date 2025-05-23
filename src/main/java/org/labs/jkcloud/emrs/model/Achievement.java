package org.labs.jkcloud.emrs.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = false)
    private String section;

    @Column(length = 2000)
    private String description;

    private String coverImage; // URL of the image uploaded from Angular
}

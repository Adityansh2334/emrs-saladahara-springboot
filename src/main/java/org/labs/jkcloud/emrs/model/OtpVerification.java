package org.labs.jkcloud.emrs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpVerification {

    @Id
    private String email;

    private String otp;

    private LocalDateTime expiryTime;

    private boolean verified;

    private LocalDateTime createdAt;
}

package org.labs.jkcloud.emrs.service;


import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.Admin;
import org.labs.jkcloud.emrs.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminManagementService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<Admin> updateAdminCredentials(String email, String newUsername, String newPassword) {
        return adminRepository.findByEmail(email).map(admin -> {
            if (newUsername != null && !newUsername.isBlank()) {
                admin.setUsername(newUsername);
            }
            if (newPassword != null && !newPassword.isBlank()) {
                admin.setPassword(passwordEncoder.encode(newPassword));
            }
            return adminRepository.save(admin);
        });
    }
}


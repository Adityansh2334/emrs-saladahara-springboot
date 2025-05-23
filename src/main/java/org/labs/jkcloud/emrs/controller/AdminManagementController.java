package org.labs.jkcloud.emrs.controller;


import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.service.AdminManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dev/manage-admins")
@RequiredArgsConstructor
public class AdminManagementController {

    private final PasswordEncoder passwordEncoder;

    private final AdminManagementService adminService;

    @PreAuthorize("hasRole('DEV')")
    @PutMapping("/update")
    public ResponseEntity<?> updateAdminCredentials(@RequestBody Map<String, String> payload) {
        String adminEmail =payload.get("adminEmail");
        String newUsername = payload.get("username");
        String newPassword = payload.get("password");

        return adminService.updateAdminCredentials(adminEmail, newUsername, newPassword)
                .map(updated -> ResponseEntity.ok(Map.of("message", "Admin credentials updated successfully.")))
                .orElse(ResponseEntity.badRequest().body(Map.of("error", "Admin not found.")));
    }

    @GetMapping("/get-bcrypt-password")
    public String getBcryptPassword(@RequestParam("password") String password) {
        return passwordEncoder.encode(password);
    }
}


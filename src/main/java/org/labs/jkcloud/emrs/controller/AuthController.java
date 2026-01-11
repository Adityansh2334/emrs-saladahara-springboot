package org.labs.jkcloud.emrs.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.OtpVerification;
import org.labs.jkcloud.emrs.model.VO.*;
import org.labs.jkcloud.emrs.repository.OtpVerificationRepository;
import org.labs.jkcloud.emrs.service.AdminManagementService;
import org.labs.jkcloud.emrs.service.EmailService;
import org.labs.jkcloud.emrs.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final OtpVerificationRepository otpRepo;
    private final AdminManagementService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthRequest request,
            HttpServletResponse response
    ) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(request.getUsername());

            ResponseCookie cookie = ResponseCookie.from("EMRS_TOKEN", token)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(8 * 60 * 60)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(new AuthResponse("Login Successful!",true));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Login Successful!", false));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("EMRS_TOKEN", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setHeader("Cache-Control", "no-store");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-auth")
    public ResponseEntity<Boolean> checkAuth(Authentication authentication) {
        // Returns true if user is authenticated
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        return ResponseEntity.ok(isAuthenticated);
    }

    /** ✅ Send OTP to official admin email */
    @GetMapping("/send-otp")
    public ResponseEntity<OtpResponse> sendOtp() {
        String email = adminService.getAdminEmail(); // get official admin email
        long requests = otpRepo.countByEmailAndCreatedAtAfter(email, LocalDateTime.now().minusMinutes(5));
        if (requests >= 3) {
            return ResponseEntity.status(429).body(new OtpResponse(false, "Too many requests"));
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        otpRepo.deleteAllByEmailAndVerified(email, false); // before saving new OTP

        OtpVerification otpRecord = new OtpVerification();
        otpRecord.setEmail(email);
        otpRecord.setOtp(otp);
        otpRecord.setExpiryTime(expiry);
        otpRecord.setCreatedAt(LocalDateTime.now());
        otpRepo.save(otpRecord);

        boolean isSent = emailService.sendOtpEmail(otp);
        if(!isSent) {
            return ResponseEntity.status(500).body(new OtpResponse(false, "Failed to send OTP"));
        }else {
            return ResponseEntity.ok(new OtpResponse(true, "OTP sent successfully"));
        }
    }

    /** ✅ Verify OTP */
    @PostMapping("/verify-otp")
    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody OtpRequest req) {
        Optional<OtpVerification> otpData = otpRepo.findById(adminService.getAdminEmail());

        if (otpData.isPresent() && otpData.get().getOtp().equals(req.getOtp())
                && otpData.get().getExpiryTime().isAfter(LocalDateTime.now())) {
            otpData.get().setVerified(true);
            otpRepo.save(otpData.get());
            return ResponseEntity.ok(new OtpResponse(true, "OTP verified successfully"));
        }
        return ResponseEntity.status(400).body(new OtpResponse(false, "Invalid OTP"));
    }

    /** ✅ Update password after OTP is verified */
    @PostMapping("/update-password")
    public ResponseEntity<OtpResponse> updatePassword(@RequestBody PasswordResetRequest req) {
        String email = adminService.getAdminEmail();
        Optional<OtpVerification> otpData = otpRepo.findById(email);
        if (otpData.isPresent() && !otpData.get().isVerified()) {
            return ResponseEntity.status(403).body(new OtpResponse(false, "OTP not verified"));
        }
        adminService.updatePassword(email, req.getNewPassword());
        otpRepo.deleteById(email); // Clear OTP
        return ResponseEntity.ok(new OtpResponse(true, "Password updated successfully"));
    }
}


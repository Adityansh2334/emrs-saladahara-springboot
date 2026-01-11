package org.labs.jkcloud.emrs.service;

import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.Admin;
import org.labs.jkcloud.emrs.repository.AdminRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
        return new User(admin.getUsername(), admin.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + admin.getRole().toUpperCase())));
    }
}

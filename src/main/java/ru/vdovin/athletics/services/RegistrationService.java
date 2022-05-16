package ru.arh.athletics.services;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserDetailsManager userDetailsManager;

    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(String username, String password) {
        User user = new User(username, passwordEncoder.encode(password),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        userDetailsManager.createUser(user);
    }
}

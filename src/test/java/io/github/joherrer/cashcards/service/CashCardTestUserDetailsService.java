package io.github.joherrer.cashcards.service;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class CashCardTestUserDetailsService implements UserDetailsService {
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public CashCardTestUserDetailsService(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
                .username("sarah1")
                .password(passwordEncoder.encode("abc123"))
                .roles("AUTHORISED")
                .build();
        UserDetails hank = users
                .username("hank3")
                .password(passwordEncoder.encode("qrs456"))
                .roles("UNAUTHORISED")
                .build();
        UserDetails kumar = users
                .username("kumar2")
                .password(passwordEncoder.encode("xyz789"))
                .roles("AUTHORISED")
                .build();

        inMemoryUserDetailsManager = new InMemoryUserDetailsManager(sarah, hank, kumar);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return inMemoryUserDetailsManager.loadUserByUsername(username);
    }
}

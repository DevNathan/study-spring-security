package com.study.study01.security.service;

import com.study.study01.domain.entity.user.User;
import com.study.study01.repository.UserRepository;
import com.study.study01.security.provider.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Username " + username + " not found")
        );

        return new SecurityUser(user);
    }
}

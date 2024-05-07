package com.study.study01.service;

import com.study.study01.domain.entity.user.User;
import com.study.study01.domain.type.Role;
import com.study.study01.dto.user.UserRegisterDTO;
import com.study.study01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        String encryptedPassword = encryptPassword(userRegisterDTO.getPassword());

        User newUser = User.builder()
                .nickname(userRegisterDTO.getNickname())
                .email(userRegisterDTO.getEmail())
                .password(encryptedPassword)
                .role(Role.USER)
                .build();
        userRepository.save(newUser);
    }

    @Override
    public void resetLoginTime(Long userId) {
        userRepository.updateLoginTime(userId);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

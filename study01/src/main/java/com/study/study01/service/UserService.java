package com.study.study01.service;

import com.study.study01.dto.user.UserRegisterDTO;

public interface UserService {

    void registerUser(UserRegisterDTO userRegisterDTO);

    void resetLoginTime(Long userId);
}

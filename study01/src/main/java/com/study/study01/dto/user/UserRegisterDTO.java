package com.study.study01.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterDTO {
    private String nickname;
    private String email;
    private String password;
}

package com.study.study01.security.handler;

import com.study.study01.domain.entity.user.User;
import com.study.study01.security.provider.SecurityUser;
import com.study.study01.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SecurityUser user = (SecurityUser) authentication.getPrincipal();

        userService.resetLoginTime(user.getUser().getId());
        setDefaultTargetUrl("/");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}

package com.study.study01.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.security.auth.login.CredentialExpiredException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        setDefaultFailureUrl("/user/login?e=true");

        Map<String, String> failureUrlMap = new HashMap<>();

        failureUrlMap.put(CredentialExpiredException.class.getName(), "/user/login?cre=true");

        setExceptionMappings(failureUrlMap);

        super.onAuthenticationFailure(request, response, exception);
    }
}

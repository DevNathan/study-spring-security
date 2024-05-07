package com.study.study01.controller.user;

import com.study.study01.dto.user.UserRegisterDTO;
import com.study.study01.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String goToLogin(UserRegisterDTO userRegisterDTO,
                            Model model) {
        model.addAttribute(userRegisterDTO);
        return "/user/login";
    }

    @PostMapping("/login")
    public String login() {
        return "/";
    }

    @PostMapping("/register")
    public String register(UserRegisterDTO userRegisterDTO) {
        System.out.println("userRegisterDTO = " + userRegisterDTO);

        userService.registerUser(userRegisterDTO);

        return "redirect:/user/login";
    }
}

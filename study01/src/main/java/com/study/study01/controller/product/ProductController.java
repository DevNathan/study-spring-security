package com.study.study01.controller.product;

import com.study.study01.domain.entity.user.User;
import com.study.study01.security.provider.SecurityUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public String goToProductPage(@AuthenticationPrincipal SecurityUser user) {
        System.out.println(user.getUsername());
        System.out.println(user.getAuthorities());

        return "/product/list";
    }

}

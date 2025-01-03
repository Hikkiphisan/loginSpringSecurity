package org.example.spring_security.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {
    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

    @GetMapping("/user")
    public ModelAndView user(Principal principal) { // đối tượng Principal (đại diện cho thông tin người dùng đã đăng nhập)
        System.out.println(principal.getName());
        return new ModelAndView("/user");
    }
    @GetMapping("/admin")
    public ModelAndView admin() {
        // Get authenticated username from SecurityContext
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getName());
        return new ModelAndView("/admin");
        // đang sử dụng SecurityContext để lấy tên người dùng đã đăng nhập và xác thực. Tuy nhiên, bạn không cần truyền tham số Principal vào như trong phương thức user vì thông tin người dùng đã có sẵn trong SecurityContext thông qua SecurityContextHolder.
    }



    @GetMapping("/login")
    public String login() {
        return "/login";  // Trả về tên của trang HTML (login.html)
    }
}



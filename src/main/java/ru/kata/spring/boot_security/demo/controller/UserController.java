package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService serviceUser;

    @Autowired
    public UserController(UserService serviceUser) {
        this.serviceUser = serviceUser;
    }

    @GetMapping()
    public String showUser(@AuthenticationPrincipal User user, Model model) {
        System.out.println(serviceUser.loadUserByUsername(user.getUsername()));
        model.addAttribute("user", user);
        System.out.println(user);
        return "user/profile";

    }

}



package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final ServiceUser serviceUser;

    public AdminController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", serviceUser.getAllUsers());
        return "users/showAllUsers";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "users/newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/newUser";
        }
        serviceUser.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String getUserById(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", serviceUser.getUserById(id));
        return "users/showUserById";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        serviceUser.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", serviceUser.g(id));
        return "users/edit";
    }

    @PostMapping("/edit")
    public String updateUser(@RequestParam("id") int id,
                             @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        serviceUser.update(id, user);
        return "redirect:/users";
    }
}

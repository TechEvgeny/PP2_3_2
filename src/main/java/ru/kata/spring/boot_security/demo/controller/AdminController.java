package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final ServiceUser serviceUser;

    public AdminController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", serviceUser.getAllUsers());
        return "admin/showAllUsers";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles = serviceUser.getAllRoles();
        model.addAttribute("rolesList", roles);
        return "admin/newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             @RequestParam("nameRoles") String[] roles,
                             BindingResult bindingResult) {

        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(serviceUser.getRoleByName(role));
        }
        user.setRoles(roleSet);
        if (bindingResult.hasErrors()) {
            return "admin/newUser";
        }
        serviceUser.save(user);
        return "redirect:/admin";
    }


    @GetMapping("/{id}")
    public String showUserById(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", serviceUser.showUser(id));
        return "admin/showUserById";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        serviceUser.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", serviceUser.showUser(id));
        model.addAttribute("rolesList", serviceUser.getAllRoles());
        return "admin/edit";
    }

    @PostMapping("/edit")
    public String updateUser(@RequestParam("id") int id, @RequestParam("editRoles") String[] roles,
                             @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(serviceUser.getRoleByName(role));
        }
        System.out.println(roleSet);
        System.out.println(user);
        user.setRoles(roleSet);
        serviceUser.update(id, user);
        return "redirect:/admin";
    }
}




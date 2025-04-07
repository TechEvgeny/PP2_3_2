package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminInit implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInit(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Role admin = roleRepository.getRoleByName("ROLE_ADMIN");
        if (admin == null) {
            admin = new Role("ROLE_ADMIN");
        }
        roleRepository.save(admin);

        Role user = roleRepository.getRoleByName("ROLE_USER");
        if (user == null) {
            user = new Role("ROLE_USER");
        }
        roleRepository.save(user);
        Set<Role> roles = new HashSet<>();
        roles.add(admin);
        roles.add(user);

        User userAdmin = userRepository.findByName("Admin");
        if (userAdmin == null) {
            userAdmin = new User(
                    "Admin",
                    40,
                    "admin1@mail.com",
                    passwordEncoder.encode("111"),
                    roles);

        }
        userRepository.save(userAdmin);
        Set<Role> roles1 = new HashSet<>();

        roles1.add(user);

        User cat = userRepository.findByName("cat");
        if (cat == null) {
            cat = new User(
                    "cat",
                    4,
                    "cat@mail.com",
                    passwordEncoder.encode("111"),
                    roles1);

        }
        userRepository.save(cat);

    }
}

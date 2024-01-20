package com.root.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.root.auth.models.Roles;
import com.root.auth.models.User;
import com.root.auth.models.UserRoles;
import com.root.auth.repository.RolesRepository;
import com.root.auth.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    class newUser {
        String username;
        String password;
        String confirmPassword;

        public newUser(String username, String password, String confirmPassword) {
            this.username = username;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }

        public String getUsername() {
            return this.username;
        }

        public String getPassword() {
            return this.password;
        }

        public String getConfirmPassword() {
            return this.confirmPassword;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new newUser("", "", ""));
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") newUser newUser, Model model) {
        String username = newUser.username;
        String password = newUser.password;
        String confirmPassword = newUser.confirmPassword;
        if (username == "" || password == "" || confirmPassword == "") {
            model.addAttribute("error", "Please fill out all fields");
            return "signup";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }
        try {
            Roles role = new Roles();
            if (rolesRepository.findByName(UserRoles.ROLE_USER) == null) {
                role = rolesRepository.save(new Roles(UserRoles.ROLE_USER));
            } else {
                role = rolesRepository.findByName(UserRoles.ROLE_USER);
            }

            User user = new User(username, password, role);

            user = userRepository.save(user);

            model.addAttribute("message", "User successfully created. Login to access account");
            return "login";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.toString());
            model.addAttribute("user", new newUser(username, "", ""));
            return "signup";
        }
    }

}

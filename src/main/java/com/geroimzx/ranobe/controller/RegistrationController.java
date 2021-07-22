package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.model.RoleEnum;
import com.geroimzx.ranobe.model.User;
import com.geroimzx.ranobe.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String registrationView(User user) {
        return "registration";
    }

    @PostMapping
    public String registrationPost(@Valid User user, BindingResult bindingResult, Model model) {
        User userFromDB = userRepo.findByUsername(user.getUsername());

        if(userFromDB != null) {
            bindingResult.rejectValue("username", "error.user", "An account already exists for this username.");
            return "registration";
        }

        user.setActive(true);

        user.setRoles(Collections.singleton(RoleEnum.USER));

        userRepo.save(user);

        return "redirect:/login";
    }

}

package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Transactional
@Controller
@RequestMapping("admin")
public class AdminPanelUserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/user")
    public String index(Model model) {
        model.addAttribute("userList", userRepo.findAll());
        return "admin/user/index";
    }

    @GetMapping("/user/{username}/edit")
    public String userEdit(@PathVariable String username, Model model) {
        return "admin/user/index";
    }

    @GetMapping("/user/{username}/delete")
    public String userDelete(@PathVariable String username, Model model) {
        if(userRepo.removeByUsername(username) == 0) {
            return "redirect:/admin/user";
        }
        return "redirect:/admin/user";
    }
}

package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.model.RanobePage;
import com.geroimzx.ranobe.repo.CommentRepo;
import com.geroimzx.ranobe.repo.MessageRepo;
import com.geroimzx.ranobe.repo.RanobePageRepo;
import com.geroimzx.ranobe.repo.RanobeVolumeRepo;
import com.geroimzx.ranobe.service.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class AdminPanelPageController {
    @Autowired
    private RanobePageRepo ranobePageRepo;

    @Autowired
    private RanobeVolumeRepo ranobeVolumeRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    Parser parser;

    Logger logger = LoggerFactory.getLogger(AdminPanelPageController.class);

    @GetMapping("/page/new")
    public String getRanobePageAdminView(RanobePage ranobePage, Model model) {
        logger.info("entered to this method get new" + ranobePage.toString());
        return "admin/page/ranobe_page_admin";
    }

    @PostMapping("/page/new")
    public String ranobePageAdminPostView(@Valid RanobePage ranobePage, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "admin/page/ranobe_page_admin";
        }
        ranobePageRepo.save(ranobePage);
        return "redirect:/ranobe/" + ranobePageRepo.findByName(ranobePage.getName()).getId();
    }

    @GetMapping("/page/edit")
    public String ranobePageEditAdminView(Model model) {
        model.addAttribute("pages", ranobePageRepo.findAll());
        return "admin/page/ranobe_page_admin_edit";
    }

    @GetMapping("/page/{id}/edit")
    public String ranobePageAdminEditView(@PathVariable("id") long id, RanobePage ranobePage, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("ranobePage", ranobePageRepo.findById(id).get());
        return "admin/page/ranobe_page_admin_edit_form";
    }

    @PatchMapping("/page/{id}/edit")
    public String ranobePageAdminEditPostView(@PathVariable("id") long id, @Valid RanobePage ranobePage, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/ranobe_page_admin_edit_form";
        }
        ranobePage.setId(id);
        ranobePageRepo.save(ranobePage);
        return "redirect:edit";
    }

    @GetMapping("/page/{id}/delete")
    public String ranobePageAdminDeleteView(@PathVariable("id") long id) {
        ranobePageRepo.deleteById(id);
        return "redirect:/admin/page/edit";
    }
}

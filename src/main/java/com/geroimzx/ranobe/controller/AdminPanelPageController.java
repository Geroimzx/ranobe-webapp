package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.model.RanobePage;
import com.geroimzx.ranobe.repo.RanobePageRepo;
import com.geroimzx.ranobe.service.impl.ImageStorageImgBBServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class AdminPanelPageController {
    @Autowired
    private RanobePageRepo ranobePageRepo;

    @Autowired
    ImageStorageImgBBServiceImpl imageStorageImgBBService;

    Logger logger = LoggerFactory.getLogger(AdminPanelPageController.class);

    @GetMapping("/page/new")
    public String getRanobePageAdminView(RanobePage ranobePage, MultipartFile image, Model model) {
        return "admin/page/ranobe_page_admin";
    }

    @PostMapping("/page/new")
    public String ranobePageAdminPostView(@Valid RanobePage ranobePage, MultipartFile image, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            logger.info("Error occurred when new page created");
            return "admin/page/ranobe_page_admin";
        }
        if(!image.isEmpty()) {
            String jsonResponse = imageStorageImgBBService.postFile(image).getBody();
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                String imgUrl = jsonObject.getJSONObject("data").getString("display_url");
                if(imgUrl != null) {
                    ranobePage.setPosterFileUrl(imgUrl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ranobePageRepo.save(ranobePage);
        return "redirect:/ranobe/" + ranobePageRepo.findByName(ranobePage.getName()).getId();
    }

    @GetMapping("/page/edit")
    public String ranobePageEditAdminView(Model model) {
        model.addAttribute("pages", ranobePageRepo.findAllOnlyBasicValue());
        return "admin/page/ranobe_page_admin_edit";
    }

    @GetMapping("/page/{id}/edit")
    public String ranobePageAdminEditView(@PathVariable("id") long id, RanobePage ranobePage, MultipartFile image, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("ranobePage", ranobePageRepo.findById(id).get());
        return "admin/page/ranobe_page_admin_edit_form";
    }

    @PatchMapping("/page/{id}/edit")
    public String ranobePageAdminEditPostView(@PathVariable("id") long id, @Valid RanobePage ranobePage, MultipartFile image, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "admin/ranobe_page_admin_edit_form";
        }
        if(!image.isEmpty()) {
            String jsonResponse = imageStorageImgBBService.postFile(image).getBody();
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                String imgUrl = jsonObject.getJSONObject("data").getString("display_url");
                if(imgUrl != null) {
                    ranobePage.setPosterFileUrl(imgUrl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ranobePage.setId(id);
        ranobePageRepo.save(ranobePage);
        return "redirect:/ranobe/" + ranobePageRepo.findByName(ranobePage.getName()).getId();
    }

    @GetMapping("/page/{id}/delete")
    public String ranobePageAdminDeleteView(@PathVariable("id") long id) {
        ranobePageRepo.deleteById(id);
        return "redirect:/admin/page/edit";
    }
}

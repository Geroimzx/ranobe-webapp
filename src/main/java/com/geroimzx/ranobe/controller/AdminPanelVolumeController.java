package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.model.RanobeVolume;
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
public class AdminPanelVolumeController {
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

    Logger logger = LoggerFactory.getLogger(AdminPanelVolumeController.class);

    @GetMapping("volume")
    public String ranobeVolumeAdminView() {
        return "admin/volume/ranobe_volume_admin";
    }

    @GetMapping("volume/new")
    public String ranobeVolumeCreateAdminGetView(RanobeVolume ranobeVolume) {
        return "admin/volume/ranobe_volume_admin_create";
    }

    @PostMapping("volume/new")
    public String ranobeVolumeCreateAdminPostView(@Valid RanobeVolume ranobeVolume, BindingResult bindingResult) {
        if(ranobePageRepo.existsById(ranobeVolume.getRanobePage().getId())) {
            if (!ranobeVolumeRepo.existsByRanobePageIdAndVolumeNum(ranobeVolume.getRanobePage().getId(), ranobeVolume.getVolumeNum())) {
                if (!bindingResult.hasErrors()) {
                    ranobeVolumeRepo.save(ranobeVolume);
                    return "redirect:/ranobe/" + ranobeVolume.getRanobePage().getId() + "/" + ranobeVolume.getVolumeNum();
                }
            } else {
                bindingResult.rejectValue("volumeNum", "error.volumeNum", "An volume number already used");
            }
        } else {
            bindingResult.rejectValue("ranobePage.id", "error.ranobePage.id", "An ranobe page id not exist");
        }
        return "admin/volume/ranobe_volume_admin_create";
    }

    @GetMapping("volume/edit")
    public String ranobeVolumeEditAdminView(Model model) {
        model.addAttribute("pages", ranobePageRepo.findAll());
        return "admin/volume/ranobe_volume_admin_edit";
    }

    @GetMapping("volume/{id}/edit")
    public String ranobeVolumeEditPageAdminView(@PathVariable Long id, Model model) {
        if(ranobePageRepo.existsById(id)) {
            model.addAttribute("page", ranobePageRepo.findById(id).get());
            model.addAttribute("volumes", ranobePageRepo.findById(id).get().getVolumes());
            return "admin/volume/ranobe_volume_admin_edit_pre";
        }
        return "error/index";
    }

    @GetMapping("volume/{parentId}/{num}/edit")
    public String ranobeVolumeEditVolumeAdminView(@PathVariable Long parentId, @PathVariable int num, RanobeVolume ranobeVolume, Model model) {
        if(ranobeVolumeRepo.existsByRanobePageIdAndVolumeNum(parentId, num)) {
            logger.info("ENTERED VOLUME EDIT FORM 1");
            model.addAttribute("ranobeVolume", ranobeVolumeRepo.findByRanobePageIdAndVolumeNum(parentId, num));
            return "admin/volume/ranobe_volume_admin_edit_form";
        }
        return "error/index";
    }

    @PatchMapping("volume/edit")
    public String ranobeVolumeCreateAdminPatchView(@Valid RanobeVolume ranobeVolume, BindingResult bindingResult) {
        if(ranobePageRepo.existsById(ranobeVolume.getRanobePage().getId())) {
            if (ranobeVolumeRepo.existsByRanobePageIdAndVolumeNum(ranobeVolume.getRanobePage().getId(), ranobeVolume.getVolumeNum())) {
                if (!bindingResult.hasErrors()) {
                    ranobeVolume.setId(ranobeVolumeRepo.
                                            findByRanobePageIdAndVolumeNum(ranobeVolume.getRanobePage().getId(), ranobeVolume.getVolumeNum()).
                                                getId());
                    ranobeVolumeRepo.save(ranobeVolume);
                    return "redirect:/ranobe/" + ranobeVolume.getRanobePage().getId() + "/" + ranobeVolume.getVolumeNum();
                }
            } else {
                bindingResult.rejectValue("volumeNum", "error.volumeNum", "An volume number never used");
            }
        } else {
            bindingResult.rejectValue("ranobePage.id", "error.ranobePage.id", "An ranobe page id not exist");
        }
        return "admin/volume/ranobe_volume_admin_edit_form";
    }
}

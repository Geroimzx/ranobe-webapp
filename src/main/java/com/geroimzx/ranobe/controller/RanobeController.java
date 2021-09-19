package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.model.Genre;
import com.geroimzx.ranobe.model.RanobePage;
import com.geroimzx.ranobe.model.RanobeVolume;
import com.geroimzx.ranobe.repo.CommentRepo;
import com.geroimzx.ranobe.repo.RanobePageRepo;
import com.geroimzx.ranobe.repo.RanobeVolumeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("ranobe")
public class RanobeController {
    @Autowired
    private RanobePageRepo ranobePageRepo;

    @Autowired
    private RanobeVolumeRepo ranobeVolumeRepo;

    @Autowired
    private CommentRepo commentRepo;

    Logger logger = LoggerFactory.getLogger(RanobeController.class);

    @GetMapping("{pageId}")
    public String getRanobePageView(@PathVariable(value = "pageId") Long pageId, Model model) {
        if (ranobePageRepo.existsById(pageId)) {
            RanobePage ranobePage = ranobePageRepo.findById(pageId).get();

            ranobePage.getVolumes().sort((o1, o2) -> o1.getVolumeNum() - o2.getVolumeNum());
            model.addAttribute("page", ranobePage);
            return "ranobe/ranobe_page";
        }
        return "error/index";
    }

    @GetMapping("{id}/{volumeNum}")
    public String getRanobeVolumeView(@PathVariable("id") Long pageId, @PathVariable("volumeNum") int volumeId, Model model) {
        if (ranobeVolumeRepo.existsByRanobePageIdAndVolumeNum(pageId, volumeId)) {
            RanobeVolume ranobeVolume = ranobeVolumeRepo.findByRanobePageIdAndVolumeNum(pageId, volumeId);

            model.addAttribute("volume", ranobeVolume);

            List<String> volumeText = Arrays.asList(ranobeVolume.getText().split("\\n"));

            model.addAttribute("volumeText", volumeText);

            if(ranobeVolumeRepo.existsByRanobePageIdAndVolumeNum(pageId, volumeId - 1)) {
                model.addAttribute("preNum", ranobeVolume.getVolumeNum() - 1);
            } else {
                model.addAttribute("preNum", -1);
            }
            if(ranobeVolumeRepo.existsByRanobePageIdAndVolumeNum(pageId, volumeId + 1)) {
                model.addAttribute("nextNum", ranobeVolume.getVolumeNum() + 1);
            } else {
                model.addAttribute("nextNum", -1);
            }

            return "ranobe/ranobe_volume";
        }
        return "error/index";
    }
}

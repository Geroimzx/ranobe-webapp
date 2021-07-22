package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.repo.CommentRepo;
import com.geroimzx.ranobe.repo.MessageRepo;
import com.geroimzx.ranobe.repo.RanobePageRepo;
import com.geroimzx.ranobe.repo.RanobeVolumeRepo;
import com.geroimzx.ranobe.service.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminPanelController {
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

    Logger logger = LoggerFactory.getLogger(AdminPanelController.class);

    @GetMapping
    public String mainAdminPageView() {
        return "admin/index";
    }
}

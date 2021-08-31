package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.model.Message;
import com.geroimzx.ranobe.model.RanobePage;
import com.geroimzx.ranobe.repo.CommentRepo;
import com.geroimzx.ranobe.repo.MessageRepo;
import com.geroimzx.ranobe.repo.RanobePageRepo;
import com.geroimzx.ranobe.repo.RanobeVolumeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private RanobePageRepo ranobePageRepo;

    @Autowired
    private RanobeVolumeRepo ranobeVolumeRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<RanobePage> pageList = new ArrayList<>();
        Iterable<RanobePage> iPage = ranobePageRepo.findAll();
        iPage.forEach(pageList::add);
        pageList.sort(new Comparator<RanobePage>() {
            @Override
            public int compare(RanobePage o1, RanobePage o2) {
                return 0;
            }
        });
        model.addAttribute("pages", pageList);
        return "index";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Model model) {
        Message message = new Message(text, tag);
        if(!text.isEmpty())
            messageRepo.save(message);
        if(tag.isEmpty()) message.setTag(" ");

        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "redirect:/main";
    }

   @PostMapping("delete")
    public String delete(@RequestParam long id, Model model) {
        if(messageRepo.existsById(id))
            messageRepo.deleteById(id);

        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "redirect:/main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);

        return "main";
    }
}

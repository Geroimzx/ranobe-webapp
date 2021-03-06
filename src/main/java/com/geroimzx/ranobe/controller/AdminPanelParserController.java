package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.model.RanobePage;
import com.geroimzx.ranobe.model.RanobeVolume;
import com.geroimzx.ranobe.repo.RanobePageRepo;
import com.geroimzx.ranobe.repo.RanobeVolumeRepo;
import com.geroimzx.ranobe.service.Parser;
import com.geroimzx.ranobe.service.impl.VolumeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminPanelParserController {
    @Autowired
    private RanobePageRepo ranobePageRepo;

    @Autowired
    private RanobeVolumeRepo ranobeVolumeRepo;

    @Autowired
    VolumeParser parser;

    Logger logger = LoggerFactory.getLogger(AdminPanelParserController.class);

    // Used in creation parsing task
    private List<RanobeVolume> embeddedList = new ArrayList<>();

    @GetMapping("volume/parser")
    public String index(Model model) {
        return "admin/parser/index";
    }

    @GetMapping("volume/parser/{id}")
    public String parse(@PathVariable(name = "id") Long parentId, @RequestParam String url,
                        @RequestParam(required = false, defaultValue = "1") int pageCount,
                        @RequestParam(required = false, defaultValue = "10000") int delay,
                        Model model)
            throws IOException, InterruptedException {

        String[] ignoredWords = {"Режим бесконечной ленты глав:",
                "Горячие клавиши:", "Предыдущая часть",
                "Следующая часть",
                "Авторизуйтесь или зарегистрируйтесь, чтобы получать уведомления о новых главах и писать комментарии."};

        List<RanobeVolume> volumeList = parser.parseWithNextPageUrl(delay, url,pageCount, ignoredWords);

        model.addAttribute("volumeTextList", volumeList.get(0));

        if(ranobePageRepo.existsById(parentId)) {
            RanobePage ranobePage = ranobePageRepo.findById(parentId).get();
            int volumesNum = ranobePage.getVolumes().size();

            for (RanobeVolume volume :
                    volumeList) {
                volume.setVolumeNum(++volumesNum);
                volume.setRanobePage(ranobePage);
            }

            ranobeVolumeRepo.saveAll(volumeList);
        }

        return "admin/parser/parser";
    }

    @GetMapping("volume/parser/{id}/proxy")
    public String parseWithProxy(@PathVariable(name = "id") Long parentId, @RequestParam String url,
                        @RequestParam(required = false, defaultValue = "1") int pageCount,
                        @RequestParam(required = false, defaultValue = "10000") int delay,
                        @RequestParam(name = "host") String proxyHost,
                        @RequestParam(name = "port") Integer proxyPort,
                        @RequestParam(name = "type") String proxyType,
                        Model model)
            throws IOException, InterruptedException {

        String[] ignoredWords = {"Режим бесконечной ленты глав:",
                "Горячие клавиши:", "Предыдущая часть",
                "Следующая часть",
                "Авторизуйтесь или зарегистрируйтесь, чтобы получать уведомления о новых главах и писать комментарии."};

        parser.setProxy(new Proxy(Proxy.Type.valueOf(proxyType), new InetSocketAddress(proxyHost, proxyPort)));

        List<RanobeVolume> volumeList = parser.parseWithNextPageUrl(delay, url,pageCount, ignoredWords);

        parser.setProxy(null);

        model.addAttribute("volumeTextList", volumeList.get(0));

        if(ranobePageRepo.existsById(parentId)) {
            RanobePage ranobePage = ranobePageRepo.findById(parentId).get();
            int volumesNum = ranobePage.getVolumes().size();

            for (RanobeVolume volume :
                    volumeList) {
                volume.setVolumeNum(++volumesNum);
                volume.setRanobePage(ranobePage);
            }

            ranobeVolumeRepo.saveAll(volumeList);
        }

        return "admin/parser/parser";
    }
}

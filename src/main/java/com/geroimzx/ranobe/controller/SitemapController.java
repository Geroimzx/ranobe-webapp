package com.geroimzx.ranobe.controller;

import com.geroimzx.ranobe.model.RanobePage;
import com.geroimzx.ranobe.model.RanobeVolume;
import com.geroimzx.ranobe.repo.RanobePageRepo;
import com.geroimzx.ranobe.repo.RanobeVolumeRepo;
import com.geroimzx.ranobe.xml.XmlUrl;
import com.geroimzx.ranobe.xml.XmlUrlSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SitemapController {
    private RanobePageRepo ranobePageRepo;

    private RanobeVolumeRepo ranobeVolumeRepo;

    public SitemapController() {
    }

    @Autowired
    public SitemapController(RanobePageRepo ranobePageRepo, RanobeVolumeRepo ranobeVolumeRepo) {
        this.ranobePageRepo = ranobePageRepo;
        this.ranobeVolumeRepo = ranobeVolumeRepo;
    }

    @GetMapping(value = "/sitemap", produces = MediaType.APPLICATION_XML_VALUE)
    public XmlUrlSet main() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        create(xmlUrlSet, "", XmlUrl.Priority.HIGH);
        create(xmlUrlSet, "/main", XmlUrl.Priority.HIGH);

        for (RanobePage page:
             ranobePageRepo.findAll()) {
            create(xmlUrlSet, "/ranobe/" + page.getId(), XmlUrl.Priority.MEDIUM);
        }

        for (RanobeVolume volume:
                ranobeVolumeRepo.findAll()) {
            create(xmlUrlSet, "/ranobe/" + volume.getRanobePage().getId() + "/" + volume.getVolumeNum(), XmlUrl.Priority.MEDIUM);
        }

        return xmlUrlSet;
    }

    private void create(XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority) {
        xmlUrlSet.addUrl(new XmlUrl("https://intery.herokuapp.com" + link, priority));
    }

}

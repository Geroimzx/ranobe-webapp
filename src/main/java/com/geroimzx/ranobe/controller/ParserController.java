package com.geroimzx.ranobe.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/parser")
public class ParserController {
    Logger logger = LoggerFactory.getLogger(ParserController.class);

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String parse(@RequestParam(required = true) String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element body = doc.body();
        logger.info("PARSING STARTED");
        Elements paragraphs = body.getElementsByTag("p");
        logger.info("PARSING ENDED");
        return parsePElement(paragraphs, "Режим бесконечной ленты глав:", "Горячие клавиши:Предыдущая частьСледующая частьАвторизуйтесь или зарегистрируйтесь, чтобы получать уведомления о новых главах и писать комментарии.");
    }

    private final String parsePElement(Elements paragraphs, String...ignoreWords) {
        StringBuilder sb = new StringBuilder();
        logger.info("STRINGBUILDER CREATED");
        for (Element paragraph : paragraphs) {
            sb.append(paragraph.text()); //+ "\n"
        }
        for (final String parseWord:
             ignoreWords) {
            int i = sb.indexOf(parseWord);
            if (i != -1) {
                sb.delete(i, i + parseWord.length());
            }
        }
        logger.info("STRINGBUILDER DO WORKS");
        return sb.toString();
    }
}

package com.geroimzx.ranobe.service.impl;

import com.geroimzx.ranobe.model.RanobeVolume;
import com.geroimzx.ranobe.service.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VolumeParser implements Parser {
    Logger logger = LoggerFactory.getLogger(Parser.class);

    private String nextUrlToParse;

    @Override
    public RanobeVolume parse(String url, String... ignoreWords) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element body = doc.body();
        RanobeVolume volume;

        logger.info("PARSING STARTED");
        Elements paragraphs = body.getElementsByTag("p"); //get volume text
        Elements element = doc.getElementsByClass("ui header"); //get volume name

        logger.info("PARSING ENDED");

        List<String> list = new ArrayList<>();
        logger.info("STRING BUILDER CREATED");

        for (Element paragraph : paragraphs) {
            list.add(paragraph.text() + "\n"); //add \n to every string with volume text
        }

        for (final String parseWord:
                ignoreWords) {
             //fix it at parsing words
            list.remove(parseWord + "\n");
        }

        logger.info("STRING BUILDER DO WORKS");

        for (Element str:
             element) {
            logger.info(str.text());
        }

        StringBuilder sb = new StringBuilder();
        for (String str:
            list) {
            sb.append(str);
        }
        volume = new RanobeVolume();

        volume.setName(element.get(0).text());
        volume.setText(sb.toString());

        return volume;
    }

    @Override
    public List<RanobeVolume> parseWithNextPageUrl(int delay, String url, int pageCount, String...ignoredWords) throws IOException, InterruptedException {
        List<RanobeVolume> volumeList = new ArrayList<>();

        nextUrlToParse = url;
        for (int i = 0; i < pageCount; i++) {
            if(nextUrlToParse != null) {
                volumeList.add(parse(nextUrlToParse, ignoredWords));
            }
            Thread.sleep(delay);
            Element nextUrlElem = Jsoup.connect(nextUrlToParse).get().body().getElementsContainingOwnText("Вперед").last();
            logger.info("Next url to parse: " + nextUrlElem.parent().attr("href"));
            nextUrlToParse = nextUrlElem.parent().attr("href");
            Thread.sleep(delay);
        }

        return volumeList;
    }
}

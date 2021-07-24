package com.geroimzx.ranobe.service.impl;

import com.geroimzx.ranobe.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageStorageImgBBServiceImpl implements ImageStorageService {
    @Value("${storage.imgBB.key}")
    private String APIKey;

    @Value("${storage.imgBB.url}")
    private String url;

    public ImageStorageImgBBServiceImpl() {

    }

    public ImageStorageImgBBServiceImpl(String APIKey, String url) {
        this.APIKey = APIKey;
        this.url = url;
    }

    @Override
    public ResponseEntity<String> postFile(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body
                = new LinkedMultiValueMap<>();
        try {
            body.add("image", Base64Utils.encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity<MultiValueMap<String, String>> requestEntity
                = new HttpEntity<>(body, headers);

        Map<String, String> param = new HashMap<>();
        param.put("key", APIKey);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity(url + "?key={key}", requestEntity, String.class, param);
    }

    @Override
    public Map<String, String> deleteFile(String fileUrl) {

        return null;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

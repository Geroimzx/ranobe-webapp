package com.geroimzx.ranobe.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@SpringBootTest
class ImageStorageImgBBServiceImplTest {
    @Autowired
    ImageStorageImgBBServiceImpl imageStorageImgBBService;

    @Test
    void postFile() {
        File file = new File("src/test/java/com/geroimzx/ranobe/service/impl/test.png");
        byte[] bytesImage = null;

        try {
           bytesImage = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultipartFile multipartFile = new MockMultipartFile(
                "imageFile",
                "imageFile.png",
                MediaType.IMAGE_PNG_VALUE,
                bytesImage
        );

        ResponseEntity<String> result = imageStorageImgBBService.postFile(multipartFile);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteFile() {
    }
}
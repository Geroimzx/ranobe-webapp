package com.geroimzx.ranobe.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public interface StorageService {

    ResponseEntity<String> postFile(MultipartFile file);

    Map<String, String> deleteFile(String fileUrl);
}

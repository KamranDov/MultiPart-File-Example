package com.crocusoft.multipartfile.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {

    String saveFile(List<MultipartFile> multipartFiles);

    byte[] getFile(String fileName);
}

package com.crocusoft.multipartfile.service.impl;

import com.crocusoft.multipartfile.service.FileUploadService;
import com.crocusoft.multipartfile.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public String saveFile(List<MultipartFile> multipartFiles) {
        StringBuilder builder = new StringBuilder();
        multipartFiles.forEach(file -> {
            builder.append(FileUploadUtil.saveFile(file));
            builder.append("\n");
        });
        return builder.toString();
    }

    @Override
    public byte[] getFile(String fileName) {
        return FileUploadUtil.getFile(fileName);
    }
}

package com.crocusoft.multipartfile.controller;

import com.crocusoft.multipartfile.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final FileUploadService fileUploadService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> fileUpload(@RequestParam("image[]") List<MultipartFile> multipartFiles) {
        return new ResponseEntity<>(fileUploadService.saveFile(multipartFiles), HttpStatus.CREATED);
    }

    @GetMapping(value = "/download",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<byte[]> fileDownload(@RequestParam("fileName") String fileName) {
    return new ResponseEntity<>(fileUploadService.getFile(fileName),HttpStatus.OK);
    }
}

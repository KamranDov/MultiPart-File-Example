package com.crocusoft.multipartfile.util;

import com.crocusoft.multipartfile.exception.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component
public class FileUploadUtil {

    private static final Path PATH = Path.of("/FileStorage");
    private static final Set<String> allowedMimeTypes = new HashSet<>();

    private FileUploadUtil() {
        allowedMimeTypes.add("image/jpeg");
        allowedMimeTypes.add("image/png");
        allowedMimeTypes.add("application/pdf");

        Path path = Path.of(PATH.toUri());
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new FileStorageException("Fayl repozitoriyası yaradılarkən xəta baş verdi" + e);
            }
        }
    }

    public static String saveFile(MultipartFile multipartFile) {

        MultipartFile file = Objects.requireNonNull(multipartFile, "Fayl göndərə bilmədiniz");
        if (!allowedMimeTypes.contains(file.getContentType())) {
            throw new UnsupportedFileTypeException("Dəstəklənməyən fayl növü");
        }
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), PATH.resolve(fileName));
            return fileName;
        } catch (IOException e) {
            throw new FileCopyException("Faylı saxlamaq mümkün olmadı" + e);
        }
    }

    public static byte[] getFile(String fileName) {
        if (fileName.isEmpty()) {
            throw new InvalidFileNameException("Fayl adı boş ola bilməz");
        }
        Path path = Paths.get(PATH + "/" + fileName);
        File file = new File(path.toAbsolutePath().toString());
        if (!file.exists()) {
            throw new ResourceNotFoundException("Göstərdiyiniz fayl tapılmadı");
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] image = new byte[(int) file.length()];
            int byteRead = 0;
            while (byteRead < image.length) {
                int byteRemaining = image.length - byteRead;
                int bytesReadThisTime = fileInputStream.read(image, byteRead, byteRemaining);
                if (bytesReadThisTime == -1) {
                    break;
                }
                byteRead += bytesReadThisTime;
            }
            if (byteRead < image.length) {
                throw new FileReadException("faylı oxuma xətası");
            }
            return image;
        } catch (IOException e) {
            throw new FileReadException("faylı oxuma xətası" + e);
        }
    }
}

package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Value("${path.to.images.folder}")
    private String imagesDir;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final ImageRepository imageRepository;

    @Override
    public Image uploadImage(int id, MultipartFile file) throws IOException {
        Image image = new Image();
        String dir;
        if (Thread.currentThread().getStackTrace()[2].getClassName().equals("AdServiceImpl")) {
            dir = imagesDir;
        }
        else {
            dir = avatarsDir;
        }
        if (file.getOriginalFilename() != null) {
            Path filePath = Path.of(dir, id + "." + getExtension(file.getOriginalFilename()));
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);

            try (
                    InputStream is = file.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 2048);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 2048)
            ) {
                bis.transferTo(bos);
            }
            image.setImageURI(filePath.toUri().getPath());
            image.setMediaType(file.getContentType());
            image.setFileSize(file.getSize());

            return imageRepository.save(image);
        }
        return null;
    }

    @Override
    public Image getImage(int id) {
        return imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
}

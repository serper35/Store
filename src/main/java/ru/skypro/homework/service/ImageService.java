package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(int id, MultipartFile image) throws IOException;

    Image getImage(int id);
}

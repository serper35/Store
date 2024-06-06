package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    @Override
    public User getMe() {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPhone("+7(911)123-3210");
        return user;
    }

    @Override
    public User getUser(String email) {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPhone("+7(911)123-3210");
        return user;
    }

    public void UpdateAvatar(int userId, MultipartFile file) throws IOException, NoSuchElementException {
        Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
        logger.info("invoked updateAvatar method for user id ({})", userId);
        User user = get(userId);
//     todo:  проверить работу на несуществующем user, придумать обработку исключения или сделать return
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException();
        }
        Path filePath = Path.of(avatarsDir, userId + "." + getExtension(originalFilename));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        user.setImage(String.valueOf(filePath)); // todo: возможно, нужно сохранять иное значение при null - пустую строку или др.
        userRepository.save(user);
    }

    public User get(int id) throws NoSuchElementException {
        return userRepository.findById(id).orElseThrow();
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

}

package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.exception.UserAlreadyExistException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private Authentication authentication;
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    @Override
    public User getMe() {
        logger.info("Invoked method getMe()");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUser(username);
    }

    @Override
    public User getUser(String email) {
        logger.info("Invoked method getUser({})", email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User: " + email + " not found."));
    }

    @Override
    public void updateAvatar(String email, MultipartFile file) throws IOException, NoSuchElementException {
        Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
        logger.info("Invoked method updateAvatar for user: ({})", email);
        User user = getUser(email);
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException();
        }
        Path filePath = Path.of(avatarsDir, user.getId() + "." + getExtension(originalFilename));
        Files.createDirectories(filePath.getParent());
        if (user.getImage() != null) {
            Path oldAvaterPath = Path.of(user.getImage());
            Files.deleteIfExists(oldAvaterPath);
        }
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        user.setImage(String.valueOf(filePath));
        userRepository.save(user);
    }

    public User get(int id) throws NoSuchElementException {
        logger.info("Invoked method get(id), for user with id: ({})", id);
        return userRepository.findById(id).orElseThrow();
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    public void createUser(Register register) {
        logger.info("Invoked method createUser({})", register.getUsername());
        if (!userExists(register.getUsername())) {
            userRepository.save(userMapper.mapToUser(register));
        } else {
            logger.error("Register() throws UserAlreadyExistException");
            throw new UserAlreadyExistException("User " + register.getUsername() + " already exist");
        }
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser) {
        User user = getMe();
        logger.info("Invoked method updateUser(updateUser), for user: ({})", user.getEmail());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        userRepository.save(user);
        return userMapper.mapToUpdateUser(getUser(user.getEmail()));
    }

//    @Override
//    public void updateUser(UserDetails userDetails) {
//        logger.info("Invoked method updateUser(userDetails), for user: ({})", userDetails.getUsername());
//        User user = getUser(userDetails.getUsername());
//        user.setEmail(userDetails.getUsername());
//        user.setPassword(userDetails.getPassword());
//        user.setRole(Role.valueOf(
//                userDetails.getAuthorities().stream()
//                        .findAny()
//                        .get()
//                        .toString()
//                        .replace("ROLE_", "").toUpperCase()));
//        userRepository.save(user);
//    }

//    @Override
//    public void deleteUser(String username) {
//        logger.info("Invoked method deleteUser({})", username);
//        userRepository.delete(getUser(username));
//    }

    @Override
    public void changePassword(NewPassword password) {
        logger.info("Invoked method changePassword()");
        User userFromDb = getMe();
        if (encoder.matches(password.getCurrentPassword(), userFromDb.getPassword())) {
            userFromDb.setPassword(encoder.encode(password.getNewPassword()));
            userRepository.save(userFromDb);
        }
    }

    @Override
    public boolean userExists(String username) {
        logger.info("Invoked method userExists({})", username);
        return userRepository.existsByEmail(username);
    }

    @Override
    public byte[] getImage(int userId) throws IOException {
        logger.info("Invoked method getImage({})", userId);
        String imagePath = get(userId).getImage();
        Path filePath = Path.of(imagePath);
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            BufferedImage image = ImageIO.read(bis);
            ImageIO.write(image, getExtension(imagePath), baos);
            return baos.toByteArray();
        }
    }

    @Override
    public MediaType getImageMediatype(int id) throws IOException {
        User user = get(id);
        return MediaType.parseMediaType(Files.probeContentType(Path.of(user.getImage())));
    }
}

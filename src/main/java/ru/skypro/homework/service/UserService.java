package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.model.User;

import java.io.IOException;
import java.util.NoSuchElementException;

public interface UserService {
    User getMe();

    User getUser(String email);

    void updateAvatar(String email, MultipartFile file) throws IOException, NoSuchElementException;

    void createUser(Register register);

    void updateUser(UserDetails user);

    void deleteUser(String username);

    void changePassword(String oldPassword, String newPassword);

    boolean userExists(String username);

}

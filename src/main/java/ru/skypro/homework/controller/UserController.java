package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    //    Обновление пароля (200/401)
    @PostMapping("set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword password) {
        userService.changePassword(password);
        return ResponseEntity.status(200).build();
    }

    //    Получение информации об авторизованном пользователе (200/401)
    @GetMapping("me")
    public ResponseEntity<UserDTO> getMe() {
        UserDTO userDTO = mapper.mapToUserDTO(userService.getMe());
        return ResponseEntity.ok(userDTO);
    }

    // Обновление информации об авторизованном пользователе (200/401)
    @PatchMapping("me")
    public ResponseEntity<UpdateUser> patchMe(@RequestBody UpdateUser user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    // Обновление аватара авторизованного пользователя (200/401)
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> setAvatar(@RequestParam @RequestBody MultipartFile image) {
        String username = userService.getMe().getEmail();
        try {
            userService.updateAvatar(username, image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/image/{userId}")
    public ResponseEntity<byte[]> getImage(@PathVariable int userId) {
        try {
            byte[] avatar = userService.getImage(userId);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(userService.getImageMediatype(userId));
            httpHeaders.setContentLength(avatar.length);
            return ResponseEntity.status(200).headers(httpHeaders).body(avatar);
        } catch (IOException | ImageNotFoundException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

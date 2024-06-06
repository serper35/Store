package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.UserService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //    Обновление пароля (200/401)
    //    Почему image - это String? Возможно потому, что это json?
    @PostMapping("set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.status(200).build();
    }

    //    Получение информации об авторизованном пользователе (200/401)
    //    Есть dto Register, который уже имеет все поля, созданные мною в классе User. А нужен ли User?
    //    А может User должен содержать в себе Register? (не логично, но все же)
    @GetMapping("me")
    public ResponseEntity<UserDTO> getMe() {
        UserMapper mapper = new UserMapper();
        UserDTO userDTO = mapper.mapToUserDTO(userService.getMe());
        return ResponseEntity.ok(userDTO);
    }

    // Обновление информации об авторизованном пользователе (200/401)
    @PatchMapping("me")
    public ResponseEntity<UpdateUser> patchMe() {
        return ResponseEntity.ok(new UpdateUser());
    }

    // Обновление аватара авторизованного пользователя (200/401)
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> setAvatar(@RequestBody MultipartFile avatar) {

        return ResponseEntity.status(200).build();
    }

}

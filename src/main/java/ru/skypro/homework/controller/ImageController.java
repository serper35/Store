package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Image image = imageService.getImage(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(image.getMediaType()));
        httpHeaders.setContentLength(image.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(image.getData());
    }
}

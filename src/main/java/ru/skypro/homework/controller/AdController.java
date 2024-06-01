package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.util.UserValidate;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(@RequestPart CreateOrUpdateAdDTO properties,
                                       @RequestPart MultipartFile image,
                                       Authentication authentication) throws IOException {
        if (authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(adService.add(properties, image, authentication.getName()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAds(@PathVariable int id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            if (adService.getAds(id) != null) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removedAd(@PathVariable int id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            ExtendedAdDTO foundAd = adService.getAds(id);
            if (foundAd == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (!UserValidate.isAdminOrOwner(authentication, foundAd.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            adService.removeAd(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAds(@PathVariable int id,
                                           @RequestParam CreateOrUpdateAdDTO properties,
                                           Authentication authentication) {
        if (authentication.isAuthenticated()) {
            ExtendedAdDTO foundAd = adService.getAds(id);
            if (foundAd == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (!UserValidate.isAdminOrOwner(authentication, foundAd.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.ok(adService.updateAds(id, properties));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAdsMe(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(adService.getAdsMe(authentication.getName()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable int id,
                                         @RequestParam MultipartFile image,
                                         Authentication authentication) throws IOException {
        if (authentication.isAuthenticated()) {
            ExtendedAdDTO foundAd = adService.getAds(id);
            if (foundAd == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if (!UserValidate.isAdminOrOwner(authentication, foundAd.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.ok(adService.updateImage(id, image));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

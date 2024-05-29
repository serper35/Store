package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;

public interface AdService {
    AdsDTO getAllAds();

    AdDTO add(CreateOrUpdateAdDTO properties, MultipartFile image, String name);
}


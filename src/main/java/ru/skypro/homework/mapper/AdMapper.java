package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

@Component
public class AdMapper {
    public AdDTO modelToAdDTO(Ad model) {
        // todo Добавить параметры, когда будут написаны соответствующие модели (User, Image)
        return new AdDTO(/*model.getPk(),...*/);
    }

    public Ad createOrUpdateAdToAd(CreateOrUpdateAdDTO dto, User author) {
        return new Ad(author, dto.getTitle(), dto.getPrice(), dto.getDescription());
    }
}

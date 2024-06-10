package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

@Component
public class AdMapper {
    public AdDTO modelToAdDTO(Ad model) {
        return new AdDTO(
                model.getAuthor().getId(),
                model.getImage().getId() + "",
                model.getPk(),
                model.getPrice(),
                model.getTitle()
        );
    }

    public Ad createOrUpdateAdToAd(CreateOrUpdateAdDTO dto, User author) {
        return new Ad(author, dto.getTitle(), dto.getPrice(), dto.getDescription());
    }

    public ExtendedAdDTO modelToExtendedAdDTO(Ad model) {
        User author = model.getAuthor();
        Image image = model.getImage();
        return new ExtendedAdDTO(model.getPk(),
                author.getFirstName(),
                author.getLastName(),
                model.getDescription(),
                author.getEmail(),
                image.getId() + "",
                author.getPhone(),
                model.getPrice(),
                model.getTitle()
        );
    }
}

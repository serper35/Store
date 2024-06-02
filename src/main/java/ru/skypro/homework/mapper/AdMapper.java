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
        // todo Добавить параметры, когда будут написаны соответствующие модели (User, Image)
        return new AdDTO(/*model.getPk(),...*/);
    }

    public Ad createOrUpdateAdToAd(CreateOrUpdateAdDTO dto, User author) {
        return new Ad(author, dto.getTitle(), dto.getPrice(), dto.getDescription());
    }

    public ExtendedAdDTO modelToExtendedAdDTO(Ad model) {
        //User author = model.getAuthor();
        Image image = model.getImage();
        // todo Добавить данные из класса User, когда он будет готов
        return new ExtendedAdDTO(model.getPk(),
                "AuthorFirstName", //author.getAuthorFirstName(),
                "AuthorLastName", //author.getAuthorLastName(),
                model.getDescription(),
                "AuthorEmail", //author.getEmail(),
                image.getImageURI(),
                "89999999999", //author.getPhone(),
                model.getPrice(),
                model.getTitle()
        );
    }
}

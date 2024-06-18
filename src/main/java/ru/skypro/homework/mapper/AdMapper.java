package ru.skypro.homework.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

@Component
@Slf4j
public class AdMapper {
    public AdDTO modelToAdDTO(Ad model) {
        return new AdDTO(
                model.getAuthor().getId(),
                "/image/" + model.getImage().getId(),
                model.getPk(),
                model.getPrice(),
                model.getTitle()
        );
    }

    public Ad createOrUpdateAdToAd(CreateOrUpdateAdDTO dto, User author) {
        return new Ad(author, dto.getTitle(), dto.getPrice(), dto.getDescription());
    }

    public ExtendedAdDTO modelToExtendedAdDTO(Ad model) {
        ExtendedAdDTO extendedAdDTO = new ExtendedAdDTO();
        extendedAdDTO.setPk(model.getPk());
        extendedAdDTO.setAuthorFirstName(model.getAuthor().getFirstName());
        extendedAdDTO.setAuthorLastName(model.getAuthor().getLastName());
        extendedAdDTO.setDescription(model.getDescription());
        extendedAdDTO.setEmail(model.getAuthor().getEmail());
        extendedAdDTO.setImage("/image/" + model.getImage().getId());
        extendedAdDTO.setPhone(model.getAuthor().getPhone());
        extendedAdDTO.setPrice(model.getPrice());
        extendedAdDTO.setTitle(model.getTitle());
        return extendedAdDTO;
    }
}

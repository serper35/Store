package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skypro.homework.model.Ad;

import java.util.List;

@Data
@AllArgsConstructor
public class AdsDTO {
    private int count;
    private List<Ad> result;
}

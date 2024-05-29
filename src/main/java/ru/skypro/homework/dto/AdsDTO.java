package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ad;

import java.util.Collection;

@Data
public class AdsDTO {
    private int count;
    private Collection<Ad> ads;
}

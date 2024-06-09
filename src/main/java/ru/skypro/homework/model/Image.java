package ru.skypro.homework.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String imageURI;
    private String mediaType;
    private long fileSize;
}

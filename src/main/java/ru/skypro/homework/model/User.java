package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;

@Entity(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Role role;
    @JsonIgnore
    private String image;
}

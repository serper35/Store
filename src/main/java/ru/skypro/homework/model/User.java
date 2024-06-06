package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ru.skypro.homework.dto.Role;

import java.util.List;

@Entity(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String image;
    @JsonIgnore
    @OneToMany(mappedBy = "pk", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Ad> ads;
}

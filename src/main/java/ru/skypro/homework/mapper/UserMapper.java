package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "image", source = "image",
            conditionExpression = "java(hasImage(user))",
            qualifiedByName = "imageToString")
    UserDTO mapToUserDTO(User user);

    UpdateUser mapToUpdateUser(User user);

    @Mapping(target = "username", source = "email")
    @Mapping(target = "authorities", source = "role", qualifiedByName = "roleToAuthorities")
    UserDetailsDTO mapToUserDetailsDTO(User user);

    @Mapping(target = "email", source = "username")
    User mapToUser(Register register);

    @Named("hasImage")
    default boolean hasImage(User user) {
        return user.getImage() != null;
    }

    @Named("imageToString")
    default String imageToString(Image image) {
        return "/image/" + image.getId();
    }

    @Named("roleToAuthorities")
    default Collection<? extends GrantedAuthority> roleToAuthorities(Role role) {

        return Arrays.stream(role.name().split(", "))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

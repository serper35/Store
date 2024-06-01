package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.User;

@Component
public class UserMapper {
        public UserDTO mapToDto(User user) {
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setPhone(user.getPhone());
            return userDTO;
        }
}

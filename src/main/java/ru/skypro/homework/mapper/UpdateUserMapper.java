package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.User;

@Component
public class UpdateUserMapper {
        public UpdateUser mapToUpdateUserDto(User user) {
            UpdateUser updateUser = new UpdateUser();
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setPhone(user.getPhone());
            return updateUser;
        }
}

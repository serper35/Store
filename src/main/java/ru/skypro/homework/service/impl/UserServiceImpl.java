package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.UserService;
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getMe() {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPhone("+7(911)123-3210");
        return user;
    }

    @Override
    public User getUser(String email) {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPhone("+7(911)123-3210");
        return user;
    }
}

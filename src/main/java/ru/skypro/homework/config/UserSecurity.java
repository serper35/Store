package ru.skypro.homework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.skypro.homework.service.AdService;

@Component
@Slf4j
public class UserSecurity {
    @Autowired
    AdService adService;

    public boolean isOwner(int id) {
        String username = adService.getAds(id).getEmail();
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return username.equals(currentUserName);
    }
}

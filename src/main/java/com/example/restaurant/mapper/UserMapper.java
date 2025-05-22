package com.example.restaurant.mapper;

import com.example.restaurant.dto.UserDTO;
import com.example.restaurant.model.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public User toEntity(UserDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        return user;
    }
}

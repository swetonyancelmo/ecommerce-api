package com.swetonyancelmo.ecommerce.mapper;

import org.springframework.stereotype.Component;

import com.swetonyancelmo.ecommerce.dtos.UserResponseDTO;
import com.swetonyancelmo.ecommerce.models.User;

@Component
public class UserMapper {
    public UserResponseDTO convertToResponseToDto(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }
}
